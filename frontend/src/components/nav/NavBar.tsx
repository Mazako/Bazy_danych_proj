import {useSelector} from "react-redux";
import {userRoleSelector} from "../../features/user/UserSlice";
import {role} from "../../features/user/UserTypes";
import React, {JSX} from "react";
import {Nav} from "react-bootstrap";
import {LinkContainer} from "react-router-bootstrap";
import {UserData} from "./UserData";

export function NavBar() {
    const role: role = useSelector(userRoleSelector)
    const positions: Array<JSX.Element> = []
    positions.push(
        <LinkContainer to="/">
            <Nav.Link eventKey="offers">Oferty</Nav.Link>
        </LinkContainer>
    )
    if (role === "GUEST") {
        positions.push(
            <LinkContainer to="/login">
                <Nav.Link eventKey="login">Logowanie</Nav.Link>
            </LinkContainer>,
            <LinkContainer to="/register">
                <Nav.Link eventKey="register">Rejestracja</Nav.Link>
            </LinkContainer>
        )
    } else if (role === "USER") {
        positions.push(
            <LinkContainer to="#">
                <Nav.Link eventKey="offers">Oferty</Nav.Link>
            </LinkContainer>
        )
    } else if (role === "ADMIN") {
        positions.push(
            <LinkContainer to="#">
                <Nav.Link eventKey="adminPanel">Zarządzanie</Nav.Link>)
            </LinkContainer>
        )
    }

    return (
        <div className="d-flex justify-content-between align-items-center me-3 border-bottom">
            <Nav fill variant="pills">
                {positions.map(position => <Nav.Item className="fs-4">{position}</Nav.Item>)}
            </Nav>
            <UserData />
        </div>
    )
}