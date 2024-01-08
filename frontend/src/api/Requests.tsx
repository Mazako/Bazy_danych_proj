import {LoginCredentials} from "../features/user/UserTypes";
import axios, {AxiosInstance} from "axios";

export type ResponseStatus = 'SUCCESS' | 'FAILURE'
export interface ResponseBody<T> {
    data: T,
    status: ResponseStatus
}

class ServerExceptionHandler {
    handle5xxError: () => ResponseBody<any>
}

export const serverExceptionHandler = new ServerExceptionHandler()

export const defaultRequester: AxiosInstance = axios.create({
    baseURL: 'http://localhost:8080'
})

export const loginRequest = (loginDto: LoginCredentials) => {
    return defaultRequester.post(`/auth/login`, loginDto);
}

export const registrationRequest = async (firstName: string, lastName: string, mail: string, password: string, phone: string): Promise<ResponseBody<void>> => {

    try {
        await defaultRequester.post('/auth/register', {
            firstName: firstName,
            lastName: lastName,
            mail: mail,
            password: password,
            phone: phone
        })
        return {data: null, status: "SUCCESS"}
    } catch (e) {
        return serverExceptionHandler.handle5xxError()
    }
}

