import {useSelector} from "react-redux";
import {userFirstNameSelector, userLastNameSelector,} from "../../features/user/UserSlice";
import React from "react";
import {Card, Col, Container, Row} from "react-bootstrap";

export function TabUserInfo() {
    const firstName = useSelector(userFirstNameSelector);
    const lastName = useSelector(userLastNameSelector);

        return (
            <Container>
                <Row className="justify-content-center">
                    <Col md={12} sm={8} xs={12}>
                        <Card className="mt-4">
                            <Card.Body>
                                <Card.Title>Moje dane</Card.Title>
                                <div className="mb-2">
                                    <strong>Imię:</strong> {firstName}
                                </div>
                                <div>
                                    <strong>Nazwisko:</strong> {lastName}
                                </div>
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
            </Container>
        );
    }