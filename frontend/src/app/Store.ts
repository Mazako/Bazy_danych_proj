import {configureStore} from "@reduxjs/toolkit";
import {userReducer} from "../features/user/UserSlice";
import {toastMessageReducer} from "../features/error/ToastMessageSlice";

export const store = configureStore({
    reducer: {
        user: userReducer,
        toastMessage: toastMessageReducer,
    }
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch