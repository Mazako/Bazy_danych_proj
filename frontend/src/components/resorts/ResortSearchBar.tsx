import React, {useRef, useState} from 'react';
import {Form, Row, Col, Card} from 'react-bootstrap';
import {SearchParams} from "../../features/resort/ResortType";

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
    };
    return (
        <Card  className="mx-4 my-2 bg-secondary-subtle">
            <Form ref={formRef} onSubmit={handleSubmit} className="mx-2 my-3">
            <Row>
                <Col>
                    <Form.Control
                        placeholder="Nazwa kurortu"
                        name="resortName"
                    />
                </Col>
                <Col>
                    <Form.Control
                        placeholder="Państwo"
                        name="country"
                    />
                </Col>
                <Col>
                    <Form.Control
                        placeholder="Miasto"
                        name="city"
                    />
                </Col>
            </Row>
            <Row className="mt-3">
                <Col>
                    <Form.Control
                        type="number"
                        placeholder="Cena min"
                        name="minPrice"
                    />
                </Col>
                <Col>
                    <Form.Control
                        type="number"
                        placeholder="Cena max"
                        name="maxPrice"
                    />
                </Col>
                <Col>
                    <Form.Control
                        type="date"
                        name="departureDate"
                    />
                </Col>
                <Col>
                    <Form.Control
                        type="date"
                        name="returnDate"
                    />
                </Col>
            </Row>
                <Row className="mt-3 justify-content-center">
                    <Col xs="auto">
                        <button type="submit" className="btn btn-secondary">Wyszukaj</button>
                    </Col>
                    <Col xs="auto">
                        <button type="button" className="btn btn-outline-secondary" onClick={onReset}>Reset</button>
                    </Col>
                </Row>
        </Form>
        </Card>
    );
};
