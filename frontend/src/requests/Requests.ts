import axios from "axios";

const url: string = 'http://localhost:8080'

export interface LoginDto {
    mail: string,
    password: string
}

export const loginRequest = (loginDto: LoginDto) => {
    alert(loginDto.mail)
    return axios.post(`${url}/auth/login`, loginDto);
}
