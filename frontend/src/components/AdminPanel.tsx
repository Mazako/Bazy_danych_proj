import React, {useState} from 'react';
import {Card, Col, Nav, Row, Tab} from 'react-bootstrap';
import './UserProfilPage.css';
import {AdminAddTour} from "./adminPanel/AdminAddTour";
import {AdminAddResort} from "./adminPanel/AdminAddResort";
import {AdminRaports} from "./adminPanel/AdminRaports";

export function AdminPanel() {
    const [activeKey, setActiveKey] = useState('addTour');

    return (
        <div className="mx-auto p-3 user-profile-container" style={{maxWidth: '1200px'}}>
            <Tab.Container activeKey={activeKey} onSelect={(key) => setActiveKey(key)}>
                <Row>
                    <Col sm={3}>
                        <Nav variant="pills" className="flex-column">
                            <Nav.Item>
                                <Nav.Link eventKey="addTour" className="nav-item-custom">Dodaj wycieczke </Nav.Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Nav.Link eventKey="addResort" className="nav-item-custom">Dodaj kurort</Nav.Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Nav.Link eventKey="raports" className="nav-item-custom">Raport popularności</Nav.Link>
                            </Nav.Item>
                        </Nav>
                    </Col>
                    <Col sm={9}>
                        <Tab.Content>
                            <Tab.Pane eventKey="addTour">
                                <Card>
                                    <Card.Body>
                                        <AdminAddTour />
                                    </Card.Body>
                                </Card>
                            </Tab.Pane>
                            <Tab.Pane eventKey="addResort">
                                <Card>
                                    <Card.Body>
                                        <AdminAddResort />
                                    </Card.Body>
                                </Card>
                            </Tab.Pane>
                            <Tab.Pane eventKey="raports">
                                <Card>
                                    <Card.Body>
                                        <AdminRaports />
                                    </Card.Body>
                                </Card>
                            </Tab.Pane>
                        </Tab.Content>
                    </Col>
                </Row>
            </Tab.Container>
        </div>
    );
}
