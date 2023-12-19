import {LoginCredentials} from "../features/user/UserTypes";
import axios, {AxiosInstance} from "axios";

const url: string = 'http://localhost:8080'
export const defaultRequester : AxiosInstance = axios.create({
    baseURL: 'http://localhost:8080'
})

export const loginRequest = (loginDto: LoginCredentials) => {
    return defaultRequester.post(`${url}/auth/login`, loginDto);
}
