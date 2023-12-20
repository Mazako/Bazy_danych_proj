import {useSelector} from "react-redux";
import {userRoleSelector} from "../../features/user/UserSlice";
import {role} from "../../features/user/UserTypes";
import React, {JSX} from "react";
import {Nav} from "react-bootstrap";

export function NavBar() {
    const role: role = useSelector(userRoleSelector)
    const positions: Array<JSX.Element> = []
    positions.push(
        <Nav.Link href="/">Oferty</Nav.Link>
    )
    if (role === "GUEST") {
        positions.push(<Nav.Link eventKey="login" href="/login">Logowanie</Nav.Link>)
    } else if (role === "USER") {
        positions.push(<Nav.Link eventKey="offers">Oferty</Nav.Link>)
    } else if (role === "ADMIN") {
        positions.push(<Nav.Link eventKey="adminPanel">Zarządzanie</Nav.Link>)
    }

    return (
        <Nav variant="pills">
            {positions.map(position => <Nav.Item>{position}</Nav.Item>)}
        </Nav>
    )
}