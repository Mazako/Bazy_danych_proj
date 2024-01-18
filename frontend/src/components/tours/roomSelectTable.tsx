import React from 'react';
import { Table, Form } from 'react-bootstrap';

const RoomSelectionTable = ({ availableRooms, selectedRooms, toggleRoomSelection }) => {
    return (
        <Table striped bordered hover>
            <thead>
            <tr>
                <th>Nazwa</th>
                <th>Ilość osób</th>
                <th>Standard</th>
                <th>Wybierz</th>
            </tr>
            </thead>
            <tbody>
            {availableRooms.map(room => (
                <tr key={room.id}>
                    <td>{room.name}</td>
                    <td>{room.personCount} os.</td>
                    <td>{room.standard}</td>
                    <td>
                        <Form.Check
                            type="checkbox"
                            checked={selectedRooms.includes(room.id)}
                            onChange={() => toggleRoomSelection(room.id)}
                        />
                    </td>
                </tr>
            ))}
            </tbody>
        </Table>
    );
};

export default RoomSelectionTable;
