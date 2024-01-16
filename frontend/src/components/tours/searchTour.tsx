import React from 'react';
import { Form, Button } from 'react-bootstrap';

const SearchTour = ({ onSubmit }) => {
    return (
        <Form onSubmit={onSubmit} className="small-form">
            <Form.Group className="mb-1">
                <Form.Label className="form-label-sm">Name</Form.Label>
                <Form.Control type="text" name="name" size="sm" />
            </Form.Group>
            <Form.Group className="mb-1">
                <Form.Label className="form-label-sm">Price</Form.Label>
                <Form.Control type="number" name="price" size="sm" />
            </Form.Group>
            <Button type="submit" size="sm" className="btn-sm mt-1">Search</Button>
        </Form>
    );
};

export default SearchTour;
