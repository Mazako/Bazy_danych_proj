import {LoginCredentials, LoginResponse} from "../features/user/UserTypes";
import axios, {AxiosError, AxiosInstance} from "axios";
import {ResponseBody} from "./ResponseBody";
import {ResortResponse, ResortsListResponse, SearchParams, ToursListResponse} from "../features/resort/ResortType";
import {
    CreateContractDto,
    RoomsResponse
} from "../features/resort/RoomsType";
import {NotificationDTO} from "../features/user/Notifications";
import {ContractDTO, ContractDTOList} from "../features/contracts/ContractsType";


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
        const response = await defaultRequester.get(`/public/api/resort/list`, {params: {page}});
        console.log(response);
        return {data: response.data, status: "SUCCESS"};
    } catch (e) {
        const err = e as AxiosError;
        if (!err.response) {
            serverExceptionHandler.handle5xxError();
        }
        throw new Error("Error fetching resorts");
    }
}
export const getResortRequest = async (id: number): Promise<ResponseBody<ResortResponse>> => {
    try {
        const response = await defaultRequester.get(`/public/api/resort`, {params: {id}});
        return {data: response.data, status: "SUCCESS"};
    } catch (e) {
        const err = e as AxiosError;
        if (!err.response) {
            serverExceptionHandler.handle5xxError();
        }
        throw new Error("Error fetching resort");
    }
};

export const searchResortsRequest = async (searchParams: SearchParams, page: number): Promise<ResponseBody<ResortsListResponse>> => {
    try {
        const response = await defaultRequester.post(`/public/api/resort/search`, {...searchParams, page: page});
        return {data: response.data, status: "SUCCESS"};
    } catch (e) {
        const err = e as AxiosError;
        if (!err.response) {
            serverExceptionHandler.handle5xxError();
        }
        throw new Error("Error searching for resorts");
    }
}
export const getToursRequest = async (resortId: number, page: number): Promise<ResponseBody<ToursListResponse>> => {
    try {
        const response = await defaultRequester.post(`/public/api/tours/incoming`, {
            resortId: resortId,
            page: page
        });
        return {data: response.data, status: "SUCCESS"};
    } catch (e) {
        const err = e as AxiosError;
        if (!err.response) {
            serverExceptionHandler.handle5xxError();
        }
        throw new Error("Error fetching tours");
    }
};

export const getFiltredToursRequest = async (resortId: number, page: number,  name: string, price: number): Promise<ResponseBody<ToursListResponse>> => {
    try {
        const response = await defaultRequester.post(`/public/api/tours/incoming`, {
            resortId: resortId,
            page: page,
            name: name,
            price: price
        });
        return {data: response.data, status: "SUCCESS"};
    } catch (e) {
        const err = e as AxiosError;
        if (!err.response) {
            serverExceptionHandler.handle5xxError();
        }
        throw new Error("Error fetching tours");
    }
};
export const getAvailableRooms = async (tourId: number): Promise<ResponseBody<RoomsResponse[]>> => {
    try {
        const response = await defaultRequester.get('/public/api/tour/availableRooms', {
            params: {
                tourId: tourId,
            }
        });
        return { data: response.data, status: "SUCCESS" };
    } catch (e) {
        const err = e as AxiosError;
        if (!err.response) {
            serverExceptionHandler.handle5xxError();
        }
        throw new Error("Error fetching tours");
    }
};

export const createContract = async (dto: CreateContractDto): Promise<ResponseBody<ContractDTO>> => {
    try {
        const response = await defaultRequester.post(`/api/contracts/add`, dto);
        return { data: response.data, status: "SUCCESS" };
    } catch (e) {
        const err = e as AxiosError;
        if (!err.response) {
            serverExceptionHandler.handle5xxError();
        }
        throw new Error("Error creating contract");
    }
};

export const getNotificationsRequest = async (): Promise<ResponseBody<NotificationDTO[]>> => {
    try {
        const response = await defaultRequester.get('/api/notifications');
        return { data: response.data, status: "SUCCESS" };
    } catch (e) {
        const err = e as AxiosError;
        if (!err.response) {
            serverExceptionHandler.handle5xxError();
        }
        throw new Error("Error getting notifications");
    }
};

export const markAsSeenRequest = async (notificationId: number): Promise<void> => {
    try {
        await defaultRequester.patch(`/api/notifications/setAsSeen?notificationId=${notificationId}`);
    } catch (e) {
        const err = e as AxiosError;
        if (err.response) {
            console.error(`Error: ${err.response.status} - ${err.response.statusText}`);
        } else if (!err.response) {
            serverExceptionHandler.handle5xxError();
        }
        throw new Error("Error marking notification as seen");
    }
};

export const getContractsByStatusRequest = async (page: number,statuses: string): Promise<ResponseBody<ContractDTOList>> => {
    try {
        const response = await defaultRequester.get(`/api/contracts`, {params: {page, statuses}});
        return {data: response.data, status: "SUCCESS"};
    } catch (e) {
        const err = e as AxiosError;
        if (!err.response) {
            serverExceptionHandler.handle5xxError();
        }
        throw new Error("Error fetching contracts for user");
    }
}
export const getRefundPriceRequest = async (contractId: number): Promise<ResponseBody<Number>> => {
    try {
        const response = await defaultRequester.get(`/api/contracts/checkRefund`, {params: {contractId}});
        return {data: response.data, status: "SUCCESS"};
    } catch (e) {
        const err = e as AxiosError;
        if (!err.response) {
            serverExceptionHandler.handle5xxError();
        }
        throw new Error("Error fetching refund");
    }
}

export const refundRequest = async (contractId: number): Promise<ResponseBody<Number>> => {
    try {
        const response = await defaultRequester.get(`/api/contracts/refund`, {params: {contractId}});
        return {data: response.data, status: "SUCCESS"};
    } catch (e) {
        const err = e as AxiosError;
        if (!err.response) {
            serverExceptionHandler.handle5xxError();
        }
        throw new Error("Error fetching refund for user");
    }
}