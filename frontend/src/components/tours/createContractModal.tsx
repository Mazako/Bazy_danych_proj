import React, {useEffect, useState} from 'react';
import {Button, Modal} from 'react-bootstrap';
import {createContract, getAvailableRooms} from "../../api/Requests";
import RoomSelectionTable from "./roomSelectTable";
import ContractInfoModal from "./contractInfoModal";

const ReservationModal = ({show, onHide, tour, resort}) => {
    const [availableRooms, setAvailableRooms] = useState([]);
    const [selectedRooms, setSelectedRooms] = useState([]);
    const [showConfirmation, setShowConfirmation] = useState(false);
    const [contractData, setContractData] = useState(null);

    useEffect(() => {
        if (show && tour) {
            const fetchAvailableRooms = async () => {
                try {
                    const response = await getAvailableRooms(tour.id);
                    setAvailableRooms(response.data);
                } catch (error) {
                    console.error("Failed to fetch available rooms:", error);
                }
            };
            fetchAvailableRooms();
        } else {
            setAvailableRooms([]);
        }
    }, [show, tour, resort]);

    const toggleRoomSelection = (roomId) => {
        setSelectedRooms(prevSelectedRooms =>
            prevSelectedRooms.includes(roomId)
                ? prevSelectedRooms.filter(id => id !== roomId)
                : [...prevSelectedRooms, roomId]
        );
    };

    const handleReservation = async () => {
        try {
            console.log(selectedRooms);
            const createContractDto = {tourId: tour.id, roomIds: selectedRooms};
            const contract = await createContract(createContractDto);
            setContractData(contract.data);
            setShowConfirmation(true);
        } catch (error) {
            console.error("Failed to create contract:", error);
        }
    };

    if (!tour) {
        return null;
    }

    return (
        <>
            <Modal show={show} onHide={onHide} className="modal-lg">
                <Modal.Header closeButton>
                    <Modal.Title>Zarezerwuj: {tour.name}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <RoomSelectionTable
                        availableRooms={availableRooms}
                        selectedRooms={selectedRooms}
                        toggleRoomSelection={toggleRoomSelection}
                    />
                </Modal.Body>
                <Modal.Footer>
                    <Button className="btn btn-secondary" onClick={onHide}>Zamknij</Button>
                    <Button className="btn btn-primary" onClick={handleReservation}>Rezerwuj</Button>
                </Modal.Footer>
            </Modal>
            <ContractInfoModal
                show={showConfirmation && contractData !== null}
                onHide={() => setShowConfirmation(false)}
                contract={contractData}
            />
        </>
    );
};

export default ReservationModal;
