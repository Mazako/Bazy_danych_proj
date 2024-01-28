import React, {useRef} from 'react';
import {Button, Card, Col, Form, InputGroup, Row} from 'react-bootstrap';

export const SearchBar = ({ onSearch, onReset }) => {
    const formRef = useRef(null);

    const handleSubmit = (e) => {
        e.preventDefault();
        const formData = new FormData(formRef.current);
        const searchParams = {
            resortName: formData.get('resortName'),
            country: formData.get('country'),
            city: formData.get('city'),
            minPrice: formData.get('minPrice'),
            maxPrice: formData.get('maxPrice'),
            departureDate: formData.get('departureDate'),
            returnDate: formData.get('returnDate')
        };
        onSearch(searchParams);
    };

    const handleReset = () => {
        formRef.current.reset();
        onReset();
    };

    return (
        <Card className="my-2 shadow-sm" style={{ marginLeft: '5rem', marginRight: '5rem' }}>
            <Card.Body>
                <Form ref={formRef} onSubmit={handleSubmit}>
                    <Row className="g-3">
                        <Col md={4}>
                            <InputGroup>
                                <InputGroup.Text>🏖️</InputGroup.Text>
                                <Form.Control placeholder="Nazwa kurortu" name="resortName" id="resort-name"/>
                            </InputGroup>
                        </Col>
                        <Col md={4}>
                            <InputGroup>
                                <InputGroup.Text>🌐</InputGroup.Text>
                                <Form.Control placeholder="Państwo" name="country" id="country"/>
                            </InputGroup>
                        </Col>
                        <Col md={4}>
                            <InputGroup>
                                <InputGroup.Text>🏙️</InputGroup.Text>
                                <Form.Control placeholder="Miasto" name="city" id="city"/>
                            </InputGroup>
                        </Col>
                        <Col md={3}>
                            <InputGroup>
                                <InputGroup.Text>💲</InputGroup.Text>
                                <Form.Control type="number" placeholder="Cena min" name="minPrice" id="min-price"/>
                            </InputGroup>
                        </Col>
                        <Col md={3}>
                            <InputGroup>
                                <InputGroup.Text>💲</InputGroup.Text>
                                <Form.Control type="number" placeholder="Cena max" name="maxPrice" id="max-price"/>
                            </InputGroup>
                        </Col>
                        <Col md={3}>
                            <Form.Control type="date" name="departureDate" id="departure-date"/>
                        </Col>
                        <Col md={3}>
                            <Form.Control type="date" name="returnDate" id="return-date"/>
                        </Col>
                        <Col xs={12} className="text-center">
                            <Button variant="primary" type="submit" id="resort-search-button">Wyszukaj</Button>
                            <Button variant="outline-secondary" onClick={handleReset} className="ms-2" id="resort-reset-button">Reset</Button>
                        </Col>
                    </Row>
                </Form>
            </Card.Body>
        </Card>
    );
};
