import React, {useEffect, useState} from 'react';
import {Button} from 'react-bootstrap';
import {getContractsByStatusRequest} from "../../api/Requests";
import Paginator from "../paging/Paginator";
import RefundModal from "./RefundModal";

export function TabContractsInfo(){
    const [contracts, setContracts] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [showRefundModal, setShowRefundModal] = useState(false);
    const [selectedContract, setSelectedContract] = useState(null);

    const fetchContracts = async () => {
        const response = await getContractsByStatusRequest(page, 'PAID,PENDING_PAYMENT,IN_PROGRESS');
        setContracts(response.data.content);
        setTotalPages(response.data.totalPages);
    };

    useEffect(() => {
        fetchContracts();
    }, [page]);

    const handleRefundClick = (contract) => {
        setSelectedContract(contract);
        setShowRefundModal(true);
    };

    const refreshContracts = () => {
        fetchContracts();
    };
    function ContractsTable({ contracts,onRefundClick}) {
        return (
            <table className="table table-hover table-striped">
                <thead>
                <tr>
                    <th>Kurort</th>
                    <th>Data</th>
                    <th>Miejsce</th>
                    <th>Ilośc osób</th>
                    <th>Cena całkowita</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                {contracts.map(contract => (
                    <tr key={contract.id}>
                        <td>{contract.resortName}</td>
                        <td>{contract.departureDate} - {contract.returnDate}</td>
                        <td>{contract.country}, {contract.city}</td>
                        <td>{contract.personCount}</td>
                        <td>{contract.totalPrice} zł</td>
                        <td>{contract.status}</td>
                        <td>
                            <Button
                                variant="outline-primary"
                                onClick={() => onRefundClick(contract)}
                                className="mx-1 my-1"
                            >
                                Zwróć {}
                            </Button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        );
    }


    return (
        <div>
            <ContractsTable contracts={contracts} onRefundClick={handleRefundClick} />
            <Paginator totalPages={totalPages} currentPage={page} onPageChange={setPage} />
            <RefundModal
                show={showRefundModal}
                onHide={() => setShowRefundModal(false)}
                contract={selectedContract}
                onRefundSuccess={refreshContracts}
            />
        </div>
    );
}