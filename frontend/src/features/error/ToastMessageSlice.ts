import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "../../app/Store";
import {v4 as uuidv4} from 'uuid'

export interface ToastMessage {
    title: string,
    description: string
}

export type ToastInitialType = {
    [key: string]: ToastMessage
}

const initialState: ToastInitialType = {}

const toastMessageSlice = createSlice({
    name: 'toastMessage',
    initialState: initialState,
    reducers: {
        createMessage: (state, action: PayloadAction<ToastMessage>) => {
            const message: ToastMessage = {
                title: action.payload.title,
                description: action.payload.description,
            }
            const id = uuidv4()
            state[id] = message
        },

        removeMessage: (state, action: PayloadAction<string>) => {
            delete state[action.payload]
        },

        create5xxErrorMessage: (state) => {
            const message: ToastMessage = {
                title: 'Błąd serwera',
                description: 'Wystąpił błąd po stronie serwera. Spróbuj ponownie za kilka minut'
            }
            state[uuidv4()] = message
        }
    }
})

export const isMessageEmptySelector = (state: RootState): boolean => Object.keys(state.toastMessage).length === 0
export const messageSelector = (state: RootState) => state.toastMessage;

export const {createMessage, removeMessage, create5xxErrorMessage} = toastMessageSlice.actions;
export const toastMessageReducer = toastMessageSlice.reducer;
