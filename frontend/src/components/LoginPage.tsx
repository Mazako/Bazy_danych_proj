import React, {useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {loginSelector, userLogIn} from "../features/user/UserSlice";
import {AppDispatch} from "../app/Store";

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
        <div>
            <form onSubmit={handleSubmit}>
                <h1>Zaloguj się</h1>
                <input onChange={e => setMail(e.target.value)}
                        placeholder='E-mail'/>
                <input onChange={e => setPassword(e.target.value)}
                       type='password'
                        placeholder='Hasło'/>
                <button>Zaloguj się</button>
            </form>
            <p>{loggedIn ? "GIT" : (triedToLogin ? "CHUJNIA" : '')}</p>
        </div>
    )
}