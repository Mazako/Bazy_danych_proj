import React from 'react';
import {
    createBrowserRouter,
    createRoutesFromElements,
    Route,
    RouterProvider
} from "react-router-dom";
import {Hello} from "../components/Hello";

function App() {
  const router = createBrowserRouter(createRoutesFromElements([
      <Route path='/' element={<Hello />}></Route>
  ]))
  return (
      <RouterProvider router={router} />
  );
}

export default App;
