import {Outlet} from "react-router";
import {NavBar} from "./nav/NavBar";

export function Hello() {
    return (
        <div>
            <NavBar />
            <h1>Siema</h1>
            <Outlet />
        </div>
    )
}