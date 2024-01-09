export interface ResponseBody<T> {
    data: T,
    status: ResponseStatus
}

export type ResponseStatus = 'SUCCESS'
    | 'FAILURE'
    | 'MAIL_ALREADY_EXISTS'
