import React, {useEffect} from 'react';
import {createBrowserRouter, createRoutesFromElements, Route, RouterProvider} from "react-router-dom";
import {Hello} from "../components/Hello";
import {LoginPage} from "../components/LoginPage";
import Cookies from "js-cookie";
import {defaultRequester, ResponseBody, serverExceptionHandler} from "../api/Requests";
import 'bootstrap/dist/css/bootstrap.min.css';
import {RegisterPage} from "../components/RegisterPage";
import {AppDispatch} from "./Store";
import {useDispatch} from "react-redux";
import {create5xxErrorMessage} from "../features/error/ToastMessageSlice";


function App() {
    const dispatch: AppDispatch = useDispatch()

    serverExceptionHandler.handle5xxError = (): ResponseBody<any> => {
        dispatch(create5xxErrorMessage())
        return {data: null, status: "FAILURE"}
    }

    useEffect(() => {
        if (Cookies.get('token')) {
            defaultRequester.defaults.headers['Authorization'] = `Bearer ${Cookies.get('token')}`
        }
    }, [])

  const router = createBrowserRouter(createRoutesFromElements([
      <Route path='/' element={<Hello />}>
          <Route path='/login' element={<LoginPage />} />
          <Route path='/register' element={<RegisterPage />} />
      </Route>
  ]))
  return (
      <RouterProvider router={router} />
  );
}

export default App;
