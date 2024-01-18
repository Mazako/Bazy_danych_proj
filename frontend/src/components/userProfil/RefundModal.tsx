import {Button, Modal, Spinner, Card, ButtonGroup} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import {getRefundPriceRequest, refundRequest} from "../../api/Requests";

const RefundModal = ({ show, onHide, contract,onRefundSuccess }) => {
    const [refundPrice, setRefundPrice] = useState(null);
    const calculatedRefund = refundPrice && contract ? (refundPrice.price * contract.totalPrice) : null;

    useEffect(() => {
        const fetchRefundPrice = async () => {
            if (contract) {
                try {
                    const response = await getRefundPriceRequest(contract.id);
                    console.log(response);
                    setRefundPrice(response.data);
                } catch (error) {
                    console.error("Error fetching refund price for contract ID:", contract.id, error);
                }
            }
        };
        fetchRefundPrice();
    }, [contract]);
    const handleRefund = async () => {
        try {
            await refundRequest(contract.id);
            console.log("Refund successful for contract ID:", contract.id);
            onHide();
            onRefundSuccess();
        } catch (error) {
            console.error("Error processing refund for contract ID:", contract.id, error);
        }
    };
    return (
        <Modal show={show} onHide={onHide}>
            <Modal.Header closeButton>
                <Modal.Title>Dokonaj zwrotu</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {refundPrice !== null ? (
                    <Card>
                        <Card.Body>
                            <Card.Title>Nazwa kurortu: {contract.resortName}</Card.Title>
                            <Card.Text>Data: {contract.departureDate} - {contract.returnDate}</Card.Text>
                            <Card.Text>Cena wycieczki: {contract.totalPrice} zł</Card.Text>
                            <Card.Text>Po zwrócie otrzymasz: {calculatedRefund}</Card.Text>
                        </Card.Body>
                    </Card>
                ) : (
                    <Spinner animation="border" />
                )}
            </Modal.Body>
            <Modal.Footer>
                <ButtonGroup className="w-100 mx-3">
                    <Button className="btn btn-secondary mx-1" onClick={onHide}>Zamknij</Button>
                    <Button className="btn btn-primary mx-1" onClick={handleRefund}>Zwróć</Button>
                </ButtonGroup>
            </Modal.Footer>
        </Modal>
    );
};

export default RefundModal;
