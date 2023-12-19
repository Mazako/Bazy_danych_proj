export interface Notification {
    id: number,
    type: string,
    content: string,
}

export type role = "ADMIN" | "USER" | "GUEST"

export interface UserState {
    firstName: string | null,
    lastName: string | null,
    token: string | null,
    loggedIn: boolean,
    role: role
    notifications: Array<Notification>
}

export interface LoginCredentials {
    mail: string,
    password: string
}

export interface LoginResponse {
    firstName: string,
    lastName: string,
    token: string,
    role: role
}
