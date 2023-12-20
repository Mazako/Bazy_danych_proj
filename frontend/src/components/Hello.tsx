import {Outlet} from "react-router";
import {NavBar} from "./nav/NavBar";

export function Hello() {
    return (
        <div className="vh-100">
            <NavBar />
            <Outlet />
        </div>
    )
}