import React, {useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {loginSelector, userLogIn} from "../features/user/UserSlice";
import {AppDispatch} from "../app/Store";
import {Button, Form} from "react-bootstrap";
import {useNavigate} from "react-router";
import {useSearchParams} from "react-router-dom";

export function LoginPage(): React.JSX.Element {
    const [mail, setMail] = useState('')
    const [password, setPassword] = useState('')
    const [triedToLogin, setTriedToLogin] = useState(null)
    const loggedIn = useSelector(loginSelector)
    const dispatch: AppDispatch = useDispatch()
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const success = searchParams.get("success")

    if (loggedIn) {
        navigate('/offers')
    }

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        await dispatch(userLogIn({mail: mail, password: password}))
        setTriedToLogin(true);
    }

    return (<div className="d-flex flex-column justify-content-start align-items-center mh-100">
            {success === 'true' && <p className="fs-3 text-success">Pomyślnie utworzono konto. Zaloguj się.</p>}
            <Form onSubmit={handleSubmit} className="border rounded p-lg-4" style={{marginTop: '90px'}}>
                <Form.Group className='mb-3' controlId='formBasicEmail'>
                    <Form.Label>Email</Form.Label>
                    <Form.Control
                        id='email-label'
                        type='text'
                        onChange={e => setMail(e.target.value)}
                        value={mail}/>
                </Form.Group>
                <Form.Group className='mb-3' controlId='formBasicPassword'>
                    <Form.Label>Hasło</Form.Label>
                    <Form.Control
                        id="password-label"
                        value={password}
                        type='password'
                        onChange={e => setPassword(e.target.value)}/>
                </Form.Group>
                <Button variant='primary' type='submit'>Zaloguj się</Button>
            </Form>
            {triedToLogin && !loggedIn && <p id="incorrect-login" className="fs-4 text-danger">Niepoprawne dane logowania</p>}
        </div>
    )
}