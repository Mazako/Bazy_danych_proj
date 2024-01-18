import React, {useState} from 'react';
import {Col, Nav, Row, Tab} from 'react-bootstrap';
import {TabUserInfo} from "./userProfil/TabUserInfo";
import {TabContractsInfo} from "./userProfil/TabContractsInfo";
import './UserProfilPage.css';
import {TabContractsHistory} from "./userProfil/TabContractsHistory";

export function UserProfilePage() {
    const [activeKey, setActiveKey] = useState('mojeDane');

    return (
        <div className="mx-auto p-3 user-profile-container" style={{maxWidth: '1200px'}}>
            <Tab.Container activeKey={activeKey} onSelect={(key) => setActiveKey(key)}>
                <Row>
                    <Col sm={3}>
                        <Nav variant="pills" className="flex-column">
                            <Nav.Item>
                                <Nav.Link eventKey="mojeDane" className="nav-item-custom">Moje Dane</Nav.Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Nav.Link eventKey="mojeRezerwacje" className="nav-item-custom">Aktualne rezerwacji</Nav.Link>
                            </Nav.Item>
                            <Nav.Item>
                                <Nav.Link eventKey="historiaRezerwacji" className="nav-item-custom">Historia rezerwacji</Nav.Link>
                            </Nav.Item>
                        </Nav>
                    </Col>
                    <Col sm={9}>
                        <Tab.Content>
                            <Tab.Pane eventKey="mojeDane">
                                <TabUserInfo/>
                            </Tab.Pane>
                            <Tab.Pane eventKey="mojeRezerwacje">
                                <TabContractsInfo/>
                            </Tab.Pane>
                            <Tab.Pane eventKey="historiaRezerwacji">
                                <TabContractsHistory/>
                            </Tab.Pane>
                        </Tab.Content>
                    </Col>
                </Row>
            </Tab.Container>
        </div>
    );
}
