import {useSelector} from "react-redux";
import {userRoleSelector} from "../../features/user/UserSlice";
import React from "react";
import {Nav} from "react-bootstrap";
import {LinkContainer} from "react-router-bootstrap";
import {UserData} from "./UserData";

export function NavBar() {
    const role = useSelector(userRoleSelector);
    const positions: Array<JSX.Element> = [];

    positions.push(
        <LinkContainer to="/offers" key="offers">
            <Nav.Link eventKey="offers">Oferty</Nav.Link>
        </LinkContainer>
    );

    if (role === "GUEST") {
        positions.push(
            <LinkContainer to="/login" key="login">
                <Nav.Link eventKey="login">Logowanie</Nav.Link>
            </LinkContainer>,
            <LinkContainer to="/register" key="register">
                <Nav.Link eventKey="register">Rejestracja</Nav.Link>
            </LinkContainer>
        );
    } else if (role === "USER") {
        positions.push(
            <LinkContainer to="/contracts" key="contracts">
                <Nav.Link eventKey="contracts">Kontrakty</Nav.Link>
            </LinkContainer>
        );
    } else if (role === "ADMIN") {
        positions.push(
            <LinkContainer to="/adminPanel" key="adminPanel">
                <Nav.Link eventKey="adminPanel">Zarządzanie</Nav.Link>
            </LinkContainer>
        );
    }

    return (
        <div className="d-flex justify-content-between align-items-center me-3 border-bottom">
            <Nav fill variant="pills" className="mx-4 my-2">
                {positions.map((position) => position)}
            </Nav>
            {(role === 'USER' || role === 'ADMIN') && (
                <>
                    <UserData/>
                </>
            )}
        </div>
    );
}

export default NavBar;
