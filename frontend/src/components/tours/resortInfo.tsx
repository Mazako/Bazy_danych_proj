import React from 'react';
import {Card, Col, Row} from "react-bootstrap";
import {GoogleMap, LoadScript, Marker} from '@react-google-maps/api';

const ResortInfo = ({ resort }) => {
    const mapStyles = {
        height: '600px',
        width: "100%"};

    const defaultCenter = {
        lat: parseFloat(resort.latitude), lng: parseFloat(resort.longitude)
    }

    return (
        <Row className="mb-4">
            <Col md={4}>
                <Card className="h-100">
                    <Card.Body>
                        <Card.Title>{resort.resortName}</Card.Title>
                        <Card.Text>Państwo: {resort.country}</Card.Text>
                        <Card.Text>Miasto: {resort.city}</Card.Text>
                        <Card.Text>Średnia opinia: {resort.averageOpinion}</Card.Text>
                        <Card.Text>{resort.description}</Card.Text>

                    </Card.Body>
                </Card>
            </Col>
            <Col md={8}>
                <LoadScript
                    googleMapsApiKey="AIzaSyAJTLnFZi0NW2R7CePgEmyBNaoIznj8jM4">
                    <GoogleMap
                        mapContainerStyle={mapStyles}
                        zoom={10}
                        center={defaultCenter}>
                        <Marker position={defaultCenter}/>
                    </GoogleMap>
                </LoadScript>
            </Col>
        </Row>
    );
};

export default ResortInfo;
