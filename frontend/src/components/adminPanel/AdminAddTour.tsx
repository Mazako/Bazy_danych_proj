import React, {useRef, useState} from 'react';
import {Alert, Button, Col, Container, Form, Modal, Row} from 'react-bootstrap';
import {createNewTourRequest} from "../../api/Requests";

export function AdminAddTour() {
    const formRef = useRef(null);
    const [formError, setFormError] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [modalContent, setModalContent] = useState('');

    async function createTourRequest(data) {
        try {
            const response = await createNewTourRequest(data);
            setModalContent(`Udało się utworzyć wycieczkę o ID: ${response.data}`);
            setShowModal(true);
        } catch (error) {
            console.error("Failed to fetch available rooms:", error);
            setModalContent(`Błąd podczas tworzenia wycieczki: ${error}`);
            setShowModal(true);
        }
    }

    const getStringValue = (formData, fieldName) => {
        const value = formData.get(fieldName);
        return typeof value === 'string' ? value.trim() : '';
    };
    const validateForm = () => {
        const formData = new FormData(formRef.current);
        const requiredFields = ['resortId', 'name', 'price', 'departureDate', 'returnDate', 'roomIds'];
        return requiredFields.every(field => getStringValue(formData, field) !== '');
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        setFormError(false);

        const formData = new FormData(formRef.current);
        const roomIdsString = formData.get('roomIds');
        let roomIds = [];
        if (typeof roomIdsString === 'string') {
            roomIds = roomIdsString.split(',').map(id => parseInt(id.trim(), 10)).filter(id => !isNaN(id));
        }
        const data = {
            resortId: formData.get('resortId'),
            name: formData.get('name'),
            description: formData.get('description'),
            price: formData.get('price'),
            departureDate: formData.get('departureDate'),
            returnDate: formData.get('returnDate'),
            facilities: {
                wifi: formData.get('wifi') === 'on',
                swimmingPool: formData.get('swimmingPool') === 'on',
                airConditioning: formData.get('airConditioning') === 'on',
                gym: formData.get('gym') === 'on',
                food: formData.get('food') === 'on',
                roomService: formData.get('roomService') === 'on',
                bar: formData.get('bar') === 'on',
                restaurant: formData.get('restaurant') === 'on',
                freeParking: formData.get('freeParking') === 'on',
                allTimeReception: formData.get('allTimeReception') === 'on'
            },
            roomIds: roomIds
        };
        if (!validateForm()) {
            setFormError(true);
            return;
        }
        await createTourRequest(data);
    };

    return (
        <Container>
            <Form ref={formRef} onSubmit={handleSubmit}>
                <Form.Group as={Row} className="mb-3">
                    <Form.Label column sm={2}>ID kurortu</Form.Label>
                    <Col sm={10}>
                        <Form.Control name="resortId" type="text"/>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="mb-3">
                    <Form.Label column sm={2}>Nazwa</Form.Label>
                    <Col sm={10}>
                        <Form.Control name="name" type="text"/>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="mb-3">
                    <Form.Label column sm={2}>Opis</Form.Label>
                    <Col sm={10}>
                        <Form.Control name="description" as="textarea" rows={3}/>
                    </Col>
                </Form.Group>

                <Form.Group as={Row} className="mb-3">
                    <Form.Label column sm={2}>Cena</Form.Label>
                    <Col sm={10}>
                        <Form.Control name="price" type="number"/>
                    </Col>
                </Form.Group>

                <Row className="mb-3">
                    <Col md={6}>
                        <Form.Group>
                            <Form.Label>Data wyjazdu</Form.Label>
                            <Form.Control name="departureDate" type="date"/>
                        </Form.Group>
                    </Col>
                    <Col md={6}>
                        <Form.Group>
                            <Form.Label>Data powrotu</Form.Label>
                            <Form.Control name="returnDate" type="date"/>
                        </Form.Group>
                    </Col>
                </Row>

                <Form.Group as={Row} className="mb-3">
                    <Form.Label column sm={2}>Pokoje (ID oddzielone przecinkami)</Form.Label>
                    <Col sm={10}>
                        <Form.Control name="roomIds" type="text"/>
                    </Col>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Udogodnienia</Form.Label>
                    <Row>
                        <Col md={4}><Form.Check name="wifi" type="checkbox" label="Wifi"/></Col>
                        <Col md={4}><Form.Check name="swimmingPool" type="checkbox" label="Basen"/></Col>
                        <Col md={4}><Form.Check name="airConditioning" type="checkbox" label="Klimatyzacja"/></Col>
                    </Row>
                    <Row>
                        <Col md={4}><Form.Check name="gym" type="checkbox" label="Siłownia"/></Col>
                        <Col md={4}><Form.Check name="food" type="checkbox" label="Wyżywienie"/></Col>
                        <Col md={4}><Form.Check name="roomService" type="checkbox" label="Obsługa pokoju"/></Col>
                    </Row>
                    <Row>
                        <Col md={4}><Form.Check name="bar" type="checkbox" label="Bar"/></Col>
                        <Col md={4}><Form.Check name="restaurant" type="checkbox" label="Restauracja"/></Col>
                        <Col md={4}><Form.Check name="freeParking" type="checkbox" label="Darmowy parking"/></Col>
                    </Row>
                    <Row>
                        <Col md={4}><Form.Check name="allTimeReception" type="checkbox" label="Recepcja 24h"/></Col>
                    </Row>
                </Form.Group>
                <Button className="primary my-3" type="submit">Dodaj wycieczkę</Button>
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
