import React from "react";
import { Card, Col } from "react-bootstrap";
import { FaPlaneArrival, FaPlaneDeparture, FaStar } from "react-icons/fa";

const ResortCard = ({ offer }) => {
    return (
        <Col md={4} className="mb-4">
            <Card className="h-100 border border-dark shadow-sm mx-1 px-2">
                <Card.Body>
                    <Card.Title className="d-flex justify-content-between align-items-center">
                        {offer.resortName}
                        <span className="badge bg-primary text-white">
                            {offer.country}
                        </span>
                    </Card.Title>
                    <Card.Text className="text-muted small">
                        {offer.city}
                    </Card.Text>
                    <div className="d-flex justify-content-between">
                        <div>
                            <FaStar className="text-warning" /> {offer.averageOpinion}/5
                        </div>
                        <div>
                            Cena: {offer.price ? `${offer.price} zł` : 'N/A'}
                        </div>
                    </div>
                    <div className="d-flex justify-content-between mt-2">
                        <div>
                            <FaPlaneDeparture /> {offer.departureData}
                        </div>
                        <div>
                            <FaPlaneArrival /> {offer.returnDate}
                        </div>
                    </div>
                </Card.Body>
            </Card>
        </Col>
    );
};

export default ResortCard;
