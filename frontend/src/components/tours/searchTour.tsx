import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';

const SearchTour = ({ onSearch }) => {
    const [name, setName] = useState('');
    const [price, setPrice] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();
        onSearch(name, price);
    };

    return (
        <Form onSubmit={handleSubmit} className="small-form">
            <Form.Group className="mb-1">
                <Form.Label className="form-label-sm">Nazwa </Form.Label>
                <Form.Control type="text" name="name" size="sm" value={name} onChange={(e) => setName(e.target.value)} />
            </Form.Group>
            <Form.Group className="mb-1">
                <Form.Label className="form-label-sm">Minimalna cena </Form.Label>
                <Form.Control type="number" name="price" size="sm" value={price} onChange={(e) => setPrice(e.target.value)} />
            </Form.Group>
            <Button type="submit" size="sm" className="btn-sm mt-1">Search</Button>
        </Form>
    );
};

export default SearchTour;
