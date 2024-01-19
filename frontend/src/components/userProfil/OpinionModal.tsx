import React, {useState} from 'react';
import {Button, Form, Modal} from 'react-bootstrap';
import {addOpinionRequest} from "../../api/Requests";

function OpinionModal({ show, handleClose, contractId, refreshContracts }) {
    const [rating, setRating] = useState(0);
    const [comment, setComment] = useState('');
    const [showConfirmation, setShowConfirmation] = useState(false);

    const handleAddOpinion = async () => {
        const addOpinionRequestDTO = {
            contractId: contractId,
            rate: rating,
            comment: comment
        };
        try {
            const response = await addOpinionRequest(addOpinionRequestDTO);
            setShowConfirmation(true);
            refreshContracts();
        } catch (error) {
            console.error('Error submitting opinion:', error);
        }
        handleClose();
    };

    const renderStars = () => {
        let stars = [];
        for (let i = 1; i <= 5; i++) {
            stars.push(
                <span key={i} onClick={() => setRating(i)}
                      style={{cursor: 'pointer', color: i <= rating ? 'gold' : 'grey'}}>
                    &#9733;
                </span>
            );
        }
        return stars;
    };
    const handleCloseConfirmation = () => {
        setShowConfirmation(false);
        handleClose();
    };

    return (
        <>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Oceń wycieczkę</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={(e) => {
                        e.preventDefault();
                        handleAddOpinion();
                    }}>
                        <Form.Group controlId="rating">
                            <Form.Label>Ilość gwiazdek </Form.Label>
                            <span className="mx-2"> {renderStars()}</span>

                        </Form.Group>
                        <Form.Group controlId="comment">
                            <Form.Label>Komentarz (maksymalnie 250 znaków)</Form.Label>
                            <Form.Control
                                as="textarea"
                                rows={3}
                                maxLength={250}
                                value={comment}
                                onChange={(e) => setComment(e.target.value)}
                            />
                        </Form.Group>
                        <div className="d-flex justify-content-center my-2">
                            <Button className="btn btn-primary mx-1" type="submit">
                                Wyślij
                            </Button>
                            <Button className="btn btn-secondary mx-1" onClick={handleClose}>
                                Zamknij
                            </Button>
                        </div>
                    </Form>
                </Modal.Body>
            </Modal>
            <Modal show={showConfirmation} onHide={handleCloseConfirmation}>
                <Modal.Header closeButton>
                    <Modal.Title>Potwierdzenie</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Opinia została pomyślnie dodana.
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={handleCloseConfirmation}>
                        OK
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default OpinionModal;
