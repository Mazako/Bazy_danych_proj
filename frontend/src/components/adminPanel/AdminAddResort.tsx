import React, { useRef, useState } from 'react';
import { Form, Button, Container, Col, Row, Modal, Alert } from 'react-bootstrap';
import { createNewResortRequest } from "../../api/Requests";
import {RoomManager} from "./CreateRooms";

export function AdminAddResort() {
    const formRef = useRef(null);
    const [formError, setFormError] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [modalContent, setModalContent] = useState('');
    const [rooms, setRooms] = useState([{ id: 0, name: '', personCount: 0, standard: 0 }]);
    const getStringValue = (formData, fieldName) => {
        const value = formData.get(fieldName);
        return typeof value === 'string' ? value.trim() : '';
    };
    const validateForm = () => {
        const formData = new FormData(formRef.current);
        const requiredFields = ['name', 'street','buildingNumber','houseNumber','cityName','latitude','longitude'];
        return requiredFields.every(field => getStringValue(formData, field) !== '');
    };

    async function createResortRequest(data) {
        try {
            const response = await createNewResortRequest(data);
            setModalContent(`Udało się utworzyć kurort o ID: ${response.data}`);
            setShowModal(true);
        } catch (error) {
            console.error("Failed to create resort:", error);
            setModalContent(`Błąd podczas tworzenia kurortu: ${error}`);
            setShowModal(true);
        }
    }

    const handleSubmit = async (event) => {
        event.preventDefault();
        setFormError(false);

        const formData = new FormData(formRef.current);
        const address = {
            street: formData.get('street'),
            buildingNumber: formData.get('buildingNumber'),
            houseNumber: formData.get('houseNumber'),
            city: {
                country: formData.get('country'),
                name: formData.get('cityName'),
                latitude: formData.get('latitude'),
                longitude: formData.get('longitude')
            }
        };
        const data = {
            name: formData.get('name'),
            description: formData.get('description'),
            address: address,
            rooms: rooms
        };

        if (!validateForm()) {
            setFormError(true);
            return;
        }
        await createResortRequest(data);
    };


    return (
        <Container>
            <Form ref={formRef} onSubmit={handleSubmit}>
                <Form.Group  className="my-1" as={Row}>
                    <Form.Label column sm={2}>Nazwa kurortu</Form.Label>
                    <Col sm={10}>
                        <Form.Control name="name" type="text" />
                    </Col>
                </Form.Group>

                <Form.Group  className="my-1" as={Row}>
                    <Form.Label column sm={2}>Opis</Form.Label>
                    <Col sm={10}>
                        <Form.Control name="description" as="textarea" rows={3} />
                    </Col>
                </Form.Group>

                <hr />

                <h5>Adres</h5>

                <Form.Group  className="my-1" as={Row}>
                    <Form.Label column sm={2}>Ulica</Form.Label>
                    <Col sm={10}>
                        <Form.Control name="street" type="text" />
                    </Col>
                </Form.Group>

                <Form.Group  className="my-1" as={Row}>
                    <Form.Label column sm={2}>Numer budynku</Form.Label>
                    <Col sm={10}>
                        <Form.Control name="buildingNumber" type="text" />
                    </Col>
                </Form.Group>

                <Form.Group  className="my-1" as={Row}>
                    <Form.Label column sm={2}>Numer mieszkania</Form.Label>
                    <Col sm={10}>
                        <Form.Control name="houseNumber" type="text" />
                    </Col>
                </Form.Group>

                <Form.Group  className="my-1" as={Row}>
                    <Form.Label column sm={2}>Miasto</Form.Label>
                    <Col sm={4}>
                        <Form.Control name="cityName" type="text" />
                    </Col>
                    <Form.Label column sm={2}>Kraj</Form.Label>
                    <Col sm={4}>
                        <Form.Control name="country" type="text" />
                    </Col>
                </Form.Group>

                <Form.Group  className="my-1" as={Row}>
                    <Form.Label column sm={2}>Szerokość geograficzna</Form.Label>
                    <Col sm={4}>
                        <Form.Control name="latitude" type="text" />
                    </Col>
                    <Form.Label column sm={2}>Długość geograficzna</Form.Label>
                    <Col sm={4}>
                        <Form.Control name="longitude" type="text" />
                    </Col>
                </Form.Group>

                <hr />

                <RoomManager rooms={rooms} setRooms={setRooms} />
                <Button className="primary" type="submit">Dodaj kurort</Button>
            </Form>

            <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Pomyśle utworzono</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {modalContent}
                </Modal.Body>
                <Modal.Footer>
                    <Button className="secondary" onClick={() => setShowModal(false)}>
                        Zamknij
                    </Button>
                </Modal.Footer>
            </Modal>
            {formError && (
                <Alert className="danger mt-3">
                    Proszę wypełnić wszystkie wymagane pola.
                </Alert>
            )}
        </Container>
    );
}