import {useSelector} from "react-redux";
import {userRoleSelector} from "../../features/user/UserSlice";
import {role} from "../../features/user/UserTypes";
import {JSX} from "react";
import {NavLink} from "react-router-dom";

export function NavBar() {
    const role: role = useSelector(userRoleSelector)
    const positions: Array<JSX.Element> = []
    positions.push(<li key='offer'><NavLink to='/'>Oferty</NavLink></li>)
    if (role === "GUEST") {
        positions.push(<li key='log'><NavLink to="/login">Logowanie</NavLink></li>)
    } else if (role === "USER") {
        positions.push(<li key='contr'><NavLink to="/">Umowy</NavLink></li>)
    } else if (role === "ADMIN") {
        positions.push(<li key='manage'><NavLink to='/'>Panel zarządzania</NavLink></li>)
    }

    return (
        <div>
            <ul>
                {positions}
            </ul>
        </div>
    )
}