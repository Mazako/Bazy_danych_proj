import React from 'react';
import { Form, Button, Col, Row } from 'react-bootstrap';

export function RoomManager({ rooms, setRooms }) {
    const addRoomField = () => {
        setRooms([...rooms, { id: 0, name: '', personCount: 0, standard: 0 }]);
    };

    const handleRoomChange = (value, index, field) => {
        const updatedRooms = rooms.map((room, idx) => {
            if (idx === index) {
                return { ...room, [field]: value };
            }
            return room;
        });
        setRooms(updatedRooms);
    };

    const removeRoomField = (index) => {
        const updatedRooms = rooms.filter((_, idx) => idx !== index);
        setRooms(updatedRooms);
    };

    return (
        <>
            <h5>Pokoje</h5>
            {rooms.map((room, index) => (
                <div className="my-2" key={index}>
                    <Form.Group as={Row}>
                        <Col sm={2}>
                            <Form.Control
                                type="number"
                                placeholder="ID"
                                value={room.id}
                                onChange={(e) => handleRoomChange(parseInt(e.target.value), index, 'id')}
                            />
                        </Col>
                        <Col sm={3}>
                            <Form.Control
                                type="text"
                                placeholder="Nazwa pokoju"
                                value={room.name}
                                onChange={(e) => handleRoomChange(e.target.value, index, 'name')}
                            />
                        </Col>
                        <Col sm={3}>
                            <Form.Control
                                type="number"
                                placeholder="Ilość osób"
                                value={room.personCount}
                                onChange={(e) => handleRoomChange(parseInt(e.target.value), index, 'personCount')}
                            />
                        </Col>
                        <Col sm={3}>
                            <Form.Control
                                type="number"
                                placeholder="Standard"
                                value={room.standard}
                                onChange={(e) => handleRoomChange(parseInt(e.target.value), index, 'standard')}
                            />
                        </Col>
                        <Col sm={1}>
                            <Button variant="danger" onClick={() => removeRoomField(index)}>Usuń</Button>
                        </Col>
                    </Form.Group>
                </div>
            ))}
            <Button className="mx-1 my-2" onClick={addRoomField}>Dodaj pokój</Button>
        </>
    );
}
