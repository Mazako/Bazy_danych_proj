import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {RootState} from "../../app/Store";
import {LoginCredentials, LoginResponse, role, UserState} from "./UserTypes";
import Cookies from "js-cookie";
import {defaultRequester, loginRequest} from "../../api/Requests";

const createInitialState = (): UserState => {
    const token = Cookies.get('token') ?? null;
    let detailsCookie: any = Cookies.get('userDetails');
    let details = {firstName: null, lastName: null, role: 'GUEST'}
    if (detailsCookie) {
        detailsCookie = JSON.parse(detailsCookie)
        details = {firstName: detailsCookie.firstName, lastName: detailsCookie.lastName, role: detailsCookie.role}
    }
    return {
        firstName: details.firstName,
        lastName: details.lastName,
        token: token,
        loggedIn: !!token,
        role: details.role as role,
        notifications: []
    }
}

const initialState: UserState = createInitialState()

export const userLogIn = createAsyncThunk('user/login', async (arg: LoginCredentials, thunkAPI): Promise<LoginResponse> => {
    const response = await loginRequest(arg)
    const data: LoginResponse = response.data;
    defaultRequester.defaults.headers['Authorization'] = `Bearer ${data.token}`

    // setTimeout(() => thunkAPI.dispatch(logout()), 10)
    return data
})

const userSlice = createSlice({
    name: 'user',
    initialState: initialState,
    reducers: {
        logout: (state: UserState) => {
            state.lastName = null
            state.firstName = null
            state.role = "GUEST"
            state.notifications = []
            state.token = null
            Cookies.remove('token')
            Cookies.remove('userDetails')
            state.loggedIn = false
            delete defaultRequester.defaults.headers['Authorization']
        }
    },
    extraReducers: (builder) => {
        builder.addCase(userLogIn.fulfilled, (state, action) => {
            const data = action.payload
            state.loggedIn = true;
            state.token = data.token
            state.firstName = data.firstName
            state.lastName = data.lastName
            state.role = data.role
            Cookies.set('token', data.token, {expires: 1 / 24})
            Cookies.set('userDetails', JSON.stringify({
                firstName: data.firstName,
                lastName: data.lastName,
                role: data.role
            }), {expires: 1 / 24})
        })
    }
})

export const loginSelector = (state: RootState) => state.user.loggedIn
export const {logout} = userSlice.actions
export const userReducer = userSlice.reducer