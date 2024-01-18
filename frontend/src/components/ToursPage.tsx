import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import {getFiltredToursRequest, getResortRequest, getToursRequest} from '../api/Requests';
import {Button, Card, Col, Container, Form, Row, Spinner, Table} from "react-bootstrap"; // Adjust the import path as needed
import './toursStyles.css';
import SearchTour from "./tours/searchTour";
import TourCard from "./tours/cardTour";
import ResortInfo from "./tours/resortInfo";
import ReservationModal from "./tours/createContractModal";

const ToursPage = () => {
    const [tours, setTours] = useState(null);
    const [resort, setResort] = useState(null);
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const id = queryParams.get('id');
    const [selectedTour, setSelectedTour] = useState(null);
    const [modalShow, setModalShow] = useState(false);
    const handleReserveClick = (tour) => {
        setSelectedTour(tour);
        setModalShow(true);
    };

    const fetchTours = async (name = '', price=null) => {
        try {
            const response = await getFiltredToursRequest(Number(id), 0, name, price);
            setTours(response.data.content);
        } catch (error) {
            console.error("Failed to fetch tours:", error);
        }
    };

    useEffect(() => {
        if (id) {
            fetchTours();
            fetchResort();
        }
    }, [id]);

    const handleSearch = (name, price) => {
        fetchTours(name, price);
    };

    const fetchResort = async () => {
        try {
            const response = await getResortRequest(Number(id));
            setResort(response.data);
        } catch (error) {
            console.error("Failed to fetch resort:", error);
        }
    };

    const renderFacilities = (facilities) => {
        return (
            <div>
                {facilities.wifi && '📶 '}
                {facilities.swimmingPool && '🏊 '}
                {facilities.airConditioning && '❄️ '}
                {facilities.gym && '🏋️ '}
                {facilities.food && '🍔 '}
                {facilities.roomService && '🛎️ '}
                {facilities.bar && '🍸 '}
                {facilities.restaurant && '🍽️ '}
                {facilities.freeParking && '🅿️ '}
                {facilities.allTimeReception && '🕒 '}
            </div>
        );
    };

    if (!tours) {
        return (
            <Container className="text-center">
                <Spinner animation="border" role="status">
                    <span className="visually-hidden">Loading...</span>
                </Spinner>
            </Container>
        );
    }
    return (
        <Container className="my-4">
            {resort && (
                <>
                    <ResortInfo resort={resort} />
                    <ReservationModal
                        show={modalShow}
                        onHide={() => setModalShow(false)}
                        tour={selectedTour}
                        resort={resort}
                    />
                </>
            )}

            <Row>
                <Col sm={12} md={3}>
                    <SearchTour onSearch={handleSearch} />
                </Col>
                <Col sm={12} md={9}>
                    {tours && tours.map((tour, index) => (
                        <TourCard
                            key={index}
                            tour={tour}
                            renderFacilities={renderFacilities}
                            onReserveClick={handleReserveClick}
                        />
                    ))}
                </Col>
            </Row>
        </Container>
    );
};
export default ToursPage;