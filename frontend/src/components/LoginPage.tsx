import React, {useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {loginSelector, userLogIn} from "../features/user/UserSlice";
import {AppDispatch} from "../app/Store";
import {Button, Form} from "react-bootstrap";

export function LoginPage(): React.JSX.Element {
    const [mail, setMail] = useState('')
    const [password, setPassword] = useState('')
    const [triedToLogin, setTriedToLogin] = useState(false)
    const loggedIn = useSelector(loginSelector)
    const dispatch: AppDispatch = useDispatch()

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        dispatch(userLogIn({mail: mail, password: password}))
        if (!loggedIn) {
            setTriedToLogin(true)
        }

    }

    return (
        <Form onSubmit={handleSubmit}>
            <Form.Group className='mb-3' controlId='formBasicEmail'>
                <Form.Label>Email</Form.Label>
                <Form.Control type='text' onChange={e => setMail(e.target.value)}/>
            </Form.Group>
            <Form.Group className='mb-3' controlId='formBasicPassword'>
                <Form.Label>Hasło</Form.Label>
                <Form.Control type='password' onChange={e => setPassword(e.target.value)}/>
            </Form.Group>
            <Button variant='primary' type='submit'>Zaloguj się</Button>
        </Form>
    )
}