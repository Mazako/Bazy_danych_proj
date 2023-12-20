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
    const [triedToLogin, setTriedToLogin] = useState(false)
    const loggedIn = useSelector(loginSelector)
    const dispatch: AppDispatch = useDispatch()
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const success = searchParams.get("success")

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        dispatch(userLogIn({mail: mail, password: password}))
        if (!loggedIn) {
            setTriedToLogin(true)
        } else {
            navigate('/')
        }

    }

    return (<div className="d-flex flex-column justify-content-start align-items-center mh-100">
            {success === 'true' && <p className="fs-1 text-success">Pomyślnie utworzono konto</p>}
        <Form onSubmit={handleSubmit} className="border rounded p-lg-4" style={{marginTop: '90px'}}>
            <Form.Group className='mb-3' controlId='formBasicEmail'>
                <Form.Label>Email</Form.Label>
                <Form.Control
                    type='text'
                    onChange={e => setMail(e.target.value)}
                    value={mail}/>
            </Form.Group>
            <Form.Group className='mb-3' controlId='formBasicPassword'>
                <Form.Label>Hasło</Form.Label>
                <Form.Control
                    value={password}
                    type='password'
                    onChange={e => setPassword(e.target.value)}/>
            </Form.Group>
            <Button variant='primary' type='submit'>Zaloguj się</Button>
        </Form>
        {triedToLogin && <p className="fs-4 text-danger">Nie udało się zalogować</p>}
        </div>
    )
}