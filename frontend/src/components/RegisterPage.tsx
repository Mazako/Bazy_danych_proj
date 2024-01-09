import React, {useEffect, useState} from "react";
import {Button, Form} from "react-bootstrap";
import {registrationRequest} from "../api/Requests";
import {useNavigate} from "react-router";
import {useDispatch} from "react-redux";
import {createMessage} from "../features/error/ToastMessageSlice";

interface ValidatedElements {
    fistName: boolean,
    lastName: boolean,
    mail: boolean,
    password: boolean,
    repeatedPassword: boolean,
    phone: boolean

}

export function RegisterPage() {
    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [mail, setMail] = useState('')
    const [password, setPassword] = useState('')
    const [repeatedPassword, setRepeatedPassword] = useState('')
    const [phone, setPhone] = useState('')
    const [everSubmitted, setEverSubmitted] = useState(false)
    const [validatedElements, setValidatedElements] = useState<ValidatedElements>({
        fistName: true,
        lastName: true,
        password: true,
        repeatedPassword: true,
        phone: true,
        mail: true
    })

    const nav = useNavigate()
    const dispatch = useDispatch()

    useEffect(() => {
        setValidatedElements({
            fistName: /^[\p{L}\s]+$/u.test(firstName), // only letters and spaces
            lastName: /^[\p{L}\s]+$/u.test(lastName), // same,
            password: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/.test(password), // min 8 len, at least one uppercase letter, at least one digit,
            repeatedPassword: password === repeatedPassword,
            phone: /^(\+48|0048)?\s?([0-9]{9}|[0-9]{3}\s?[0-9]{3}\s?[0-9]{3})$/.test(phone), // NOSONAR,
            mail: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(mail), // regex valid with RFC 5322 and RFC 5321 standards
        })
    }, [firstName, lastName, password, repeatedPassword, phone, mail])

    const createFormLabel = (validatedElement: boolean, label: string, validationErrorMessage: string) => {
        if (everSubmitted && !validatedElement) {
            return (<Form.Label className='w-100 d-flex justify-content-between'>
                <span className='d-inline-block'>
                    {label}
                </span>
                <span className='text-danger text-end d-inline-block'>
                    ({validationErrorMessage})
                </span>
            </Form.Label>)
        }
        return <Form.Label>{label}</Form.Label>
    }

    const validateForm = (): boolean => {
        return validatedElements.fistName && validatedElements.lastName && validatedElements.password
            && validatedElements.repeatedPassword && validatedElements.mail && validatedElements.phone;
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        setEverSubmitted(true)
        if (validateForm()) {
            const response = await registrationRequest(firstName, lastName, mail, password, phone)
            if (response.status === 'SUCCESS') {
                dispatch(createMessage({
                    title: 'Utworzono konto',
                    description: 'Zweryfikuj swoje konto przez link w wiadmości wysłanej na Twoją skrzynkę pocztową'
                }))
                nav('/')
            } else if (response.status === 'MAIL_ALREADY_EXISTS') {
                dispatch(createMessage({
                    title: 'Adres E-mail w użyciu',
                    description: `Wybrany adres E-mail: ${mail} jest już w użyciu.`
                }))
                setValidatedElements(prev => {
                    return {...prev, mail: false}
                })
            }
        }
    }

    return (<div className='d-flex justify-content-center'>
        <Form onSubmit={handleSubmit} className="border rounded p-lg-4 w-50" style={{marginTop: '90px'}}>
            <Form.Group className='mb-3'>
                {createFormLabel(validatedElements.fistName, 'Imię', 'Imię nie może być puste i musi się składać z samych liter i spacji')}
                <Form.Control
                    type='text'
                    onChange={e => setFirstName(e.target.value)}
                    value={firstName}
                    isInvalid={everSubmitted && !validatedElements.fistName}/>
            </Form.Group>

            <Form.Group className='mb-3'>
                {createFormLabel(validatedElements.lastName, 'Nazwisko', 'Nazwisko nie może być puste i musi się składać z samych liter i spacji')}
                <Form.Control
                    type='text'
                    onChange={e => setLastName(e.target.value)}
                    value={lastName}
                    isInvalid={everSubmitted && !validatedElements.lastName}/>
            </Form.Group>

            <Form.Group className='mb-3'>
                {createFormLabel(validatedElements.mail, 'E-mail', 'Niepoprawny adres E-mail')}
                <Form.Control
                    type='text'
                    onChange={e => setMail(e.target.value)}
                    value={mail}
                    isInvalid={everSubmitted && !validatedElements.mail}/>
            </Form.Group>

            <Form.Group className='mb-3'>
                {createFormLabel(validatedElements.password, 'Hasło', 'Hasło musi mieć minimum 8 znaków i zawierać minimum jedną wielką literę i cyfrę')}
                <Form.Control
                    value={password}
                    type='password'
                    onChange={e => setPassword(e.target.value)}
                    isInvalid={everSubmitted && !validatedElements.password}/>
            </Form.Group>

            <Form.Group className='mb-3'>
                {createFormLabel(validatedElements.repeatedPassword, 'Powtórz hasło', 'Hasła się nie zgadzają')}
                <Form.Control
                    value={repeatedPassword}
                    type='password'
                    onChange={e => setRepeatedPassword(e.target.value)}
                    isInvalid={everSubmitted && !validatedElements.repeatedPassword}/>
            </Form.Group>

            <Form.Group className='mb-3'>
                {createFormLabel(validatedElements.phone, 'Numer telefonu', 'Niepoprawny numer telefonu')}
                <Form.Control
                    value={phone}
                    type='tel'
                    onChange={e => setPhone(e.target.value)}
                    isInvalid={everSubmitted && !validatedElements.phone}/>
            </Form.Group>

            <Button variant='primary' type='submit'>Zarejestruj się</Button>
        </Form>
    </div>)
}