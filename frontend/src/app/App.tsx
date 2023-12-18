import React from 'react';
import {
    createBrowserRouter,
    createRoutesFromElements,
    Route,
    RouterProvider
} from "react-router-dom";
import {Hello} from "../components/Hello";
import {LoginPage} from "../components/LoginPage";

function App() {
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
