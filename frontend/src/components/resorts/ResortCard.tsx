import React from "react";
import { Card, Col } from "react-bootstrap";
import {FaPlaneArrival, FaPlaneDeparture, FaStar } from "react-icons/fa";

const ResortCard = ({ offer }) => {
    const getBackgroundColor = (letter) => {
        const unicode = letter.charCodeAt(0);
        return `#${(unicode * 1234567).toString(16).substr(0, 6)}`;
    };

    const countryBackgroundColor = getBackgroundColor(offer.country.charAt(0));

    return (
        <Col key={offer.resortName} md={4}>
            <Card className="h-100 mx-4">
                <Card.Body>
                    <Card.Title className="d-flex justify-content-between align-items-center">
                        {offer.resortName}
                        <span style={{
                                backgroundColor: countryBackgroundColor,
                                padding: "2px 6px",
                                borderRadius: "4px",
                                color: "white",
                                border: "1px solid black",
                            }}>
                        {offer.country}
                       </span>
                    </Card.Title>
                    <Card.Text className="text-muted">

                    </Card.Text>

                    <div className="d-flex justify-content-between align-items-center">
                        <div>
                            { offer.city}
                        </div>

                        <div>
                            Ocena <FaStar /> {offer.averageOpinion}/5 || Cena: {offer.price ? `${offer.price} zł` : 'N/A'}
                        </div>
                    </div>

                    <div className="d-flex justify-content-between align-items-center mt-2">
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
