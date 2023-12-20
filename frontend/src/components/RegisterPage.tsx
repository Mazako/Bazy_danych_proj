import React, {useState} from "react";
import {Form} from "react-bootstrap";

export function RegisterPage(){
    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [mail, setMail] = useState('')
    const [password, setPassword] = useState('')
    const [phone, setPhone] = useState('')

    return (<>
        <h1>Rejestracja</h1>
        <Form>

        </Form>
    </>)

}