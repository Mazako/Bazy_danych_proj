import React, {useState} from "react";
import {loginRequest} from "../requests/Requests";

export function LoginPage(): React.JSX.Element {
    const [mail, setMail] = useState('')
    const [password, setPassword] = useState(' ')
    const [token, setToken] = useState('');
    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        loginRequest({mail: mail, password: password})
            .then(resp => setToken(resp.data))
            .catch(err => {
                alert(err.response.status)
            })
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
            <p>{token || 'nie ma'}</p>
        </div>
    )
}