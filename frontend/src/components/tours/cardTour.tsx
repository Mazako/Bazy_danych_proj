// TourCard.js
import React from 'react';
import {Card, Col, Row} from 'react-bootstrap';

const TourCard = ({tour, renderFacilities, onReserveClick}) => {

    return (
        <Row className="mb-3">
            <Col xs={12}>
                <Card>
                    <Card.Header as="h5" className="bg-light d-flex justify-content-between align-items-center">
                        {tour.name}
                        <span className="badge bg-dark-subtle text-success">{tour.price.toFixed(2)} zł</span>
                    </Card.Header>
                    <Card.Body className="d-flex flex-column justify-content-between">
                        <div className="mb-2">
                            <strong>Data wylotu:</strong> {tour.departureDate}<br/>
                            <strong>Data powrotu:</strong> {tour.returnDate}
                        </div>
                        <div className="facilities-icons d-flex justify-content-between align-items-center">
                            <div>
                                <strong>Zawiera w sobie:</strong> {renderFacilities(tour.facilities)}
                            </div>
                            <button className="btn btn-outline-primary"
                                    onClick={() => onReserveClick(tour)}>Zarezerwuj
                            </button>
                        </div>
                    </Card.Body>
                </Card>
            </Col>
        </Row>

    );
};

export default TourCard;
