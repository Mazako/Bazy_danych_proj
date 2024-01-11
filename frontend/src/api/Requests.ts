import {LoginCredentials, LoginResponse} from "../features/user/UserTypes";
import axios, {AxiosError, AxiosInstance} from "axios";
import {ResponseBody} from "./ResponseBody";
import {ResortsListResponse, SearchParams} from "../features/resort/ResortType";


class ServerExceptionHandler {
    handle5xxError: () => ResponseBody<any>
}

export const serverExceptionHandler = new ServerExceptionHandler()

export const defaultRequester: AxiosInstance = axios.create({
    baseURL: 'http://localhost:8080'
})

export const loginRequest = async (loginDto: LoginCredentials): Promise<ResponseBody<LoginResponse>> => {
    try {
        const response = await defaultRequester.post(`/auth/login`, loginDto);
        return {data: response.data, status: "SUCCESS"}
    } catch (e) {
        const err = e as AxiosError
        if (!err.response) {
            serverExceptionHandler.handle5xxError();
        }
        throw new Error("Logging in exception")
    }
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
        const err = e as AxiosError
        if (!err.response) {
            return serverExceptionHandler.handle5xxError();
        } else if (err.response.data === 'MAIL_ALREADY_EXISTS') {
            return {data: null, status: 'MAIL_ALREADY_EXISTS'}
        }
    }
}
export const getResortsRequest = async (page: number): Promise<ResponseBody<ResortsListResponse>> => {
    try {
        const response = await defaultRequester.get(`/api/resort/list`, { params: { page } });
        return { data: response.data, status: "SUCCESS" };
    } catch (e) {
        const err = e as AxiosError;
        if (!err.response) {
            serverExceptionHandler.handle5xxError();
        }
        throw new Error("Error fetching resorts");
    }
}

export const searchResortsRequest = async (searchParams: SearchParams, page: number): Promise<ResponseBody<ResortsListResponse>> => {
    try {
        const response = await defaultRequester.post(`/api/resort/search`, {...searchParams, page: page});
        return { data: response.data, status: "SUCCESS" };
    } catch (e) {
        const err = e as AxiosError;
        if (!err.response) {
            serverExceptionHandler.handle5xxError();
        }
        throw new Error("Error searching for resorts");
    }
}



