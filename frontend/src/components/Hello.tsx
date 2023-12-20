import {Outlet} from "react-router";
import {NavBar} from "./nav/NavBar";

export function Hello() {
    return (
        <div>
            <NavBar />
            <Outlet />
        </div>
    )
}