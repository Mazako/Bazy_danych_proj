import React from 'react';
import {createBrowserRouter, createRoutesFromElements, Route, RouterProvider} from "react-router-dom";
import {MainPage} from "../components/MainPage";
import {LoginPage} from "../components/LoginPage";
import Cookies from "js-cookie";
import {defaultRequester, serverExceptionHandler} from "../api/Requests";
import 'bootstrap/dist/css/bootstrap.min.css';
import {RegisterPage} from "../components/RegisterPage";
import {AppDispatch} from "./Store";
import {useDispatch} from "react-redux";
import {create5xxErrorMessage} from "../features/error/ToastMessageSlice";
import {ResponseBody} from "../api/ResponseBody";
import {OffersPage} from "../components/OffersPage";
import {UserProfilePage} from '../components/UserProfilePage';
import {AdminPanel} from "../components/adminPanel/AdminPanel";


function App() {
    const dispatch: AppDispatch = useDispatch()

    serverExceptionHandler.handle5xxError = (): ResponseBody<any> => {
        dispatch(create5xxErrorMessage())
        return {data: null, status: "FAILURE"}
    }
    if (Cookies.get('token')) {
        defaultRequester.defaults.headers['Authorization'] = `Bearer ${Cookies.get('token')}`
    }

    const router = createBrowserRouter(createRoutesFromElements([
        <Route path='/' element={<MainPage/>}>
            <Route path='/offers' element={<OffersPage/>}></Route>
            <Route path='/profile' element={<UserProfilePage/>}/>
            <Route path='/login' element={<LoginPage/>}/>
            <Route path='/adminPanel' element={<AdminPanel/>}/>
            <Route path='/register' element={<RegisterPage/>}/>
        </Route>
    ]))
    return (
        <RouterProvider router={router}/>
    );
}

export default App;
