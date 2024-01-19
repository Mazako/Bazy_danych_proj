import React from 'react';
import {Button, Modal} from 'react-bootstrap';

const ContractInfoModal = ({ show, onHide, contract }) => {
    if (!contract) {
        return null;
    }
    return (
        <Modal show={show} onHide={onHide} centered>
            <Modal.Header closeButton>
                <Modal.Title><strong>{contract.resortName}</strong> zarezerwowano</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <p>Data wyloty: {contract.departureDate}</p>
                <p>Data powrotu: {contract.returnDate}</p>
                <p>Miasto: {contract.city}</p>
                <p>Państwo: {contract.country}</p>
                <p>Cena: {contract.totalPrice}</p>
                <h5>Lista pokoi:</h5>
                <ul>
                    {contract.rooms.map(room => (
                        <li>
                            Pokój: {room.name}, Liczba osób: {room.personCount}, Standard: {room.standard}
                        </li>
                    ))}
                </ul>
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={onHide}>Close</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default ContractInfoModal;