import React, {useEffect} from 'react';
import {
    createBrowserRouter,
    createRoutesFromElements,
    Route,
    RouterProvider
} from 'react-router-dom';
import {Hello} from "../components/Hello";
import {LoginPage} from "../components/LoginPage";
import Cookies from "js-cookie";
import {defaultRequester} from "../api/Requests";
import 'bootstrap/dist/css/bootstrap.min.css';


function App() {

    useEffect(() => {
        if (Cookies.get('token')) {
            defaultRequester.defaults.headers['Authorization'] = `Bearer ${Cookies.get('token')}`

        }
    }, [])

  const router = createBrowserRouter(createRoutesFromElements([
      <Route path='/' element={<Hello />}>
          <Route path='/login' element={<LoginPage />} />
      </Route>
  ]))
  return (
      <RouterProvider router={router} />
  );
}

export default App;
