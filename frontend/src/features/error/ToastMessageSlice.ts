import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {RootState} from "../../app/Store";

export interface ToastMessage {
    title: string,
    description: string
}

const initialState: ToastMessage = {
    title: '',
    description: ''
}

const toastMessageSlice = createSlice({
    name: 'toastMessage',
    initialState: initialState,
    reducers: {
        createMessage: (state, action: PayloadAction<ToastMessage>) => {
            state.description = action.payload.description
            state.title = action.payload.title
        },

        removeMessage: (state) => {
            state.title = ''
            state.description = ''
        },

        create5xxErrorMessage: (state) => {
            state.title = 'Błąd serwera'
            state.description = 'Wystąpił błąd po stronie serwera. Spróbuj ponownie za kilka minut'
        }
    }
})

export const isMessageEmptySelector = (state: RootState): boolean => state.toastMessage.title === '' && state.toastMessage.description === ''
export const messageSelector = (state: RootState) => state.toastMessage;

export const {createMessage, removeMessage, create5xxErrorMessage} = toastMessageSlice.actions;
export const toastMessageReducer = toastMessageSlice.reducer;
