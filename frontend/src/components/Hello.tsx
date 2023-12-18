import {Outlet} from "react-router";

export function Hello() {
    return (
        <div>
            <h1>Siema</h1>
            <Outlet />
        </div>
    )
}