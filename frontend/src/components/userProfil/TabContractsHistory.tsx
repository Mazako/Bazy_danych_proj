import React, { useState, useEffect } from 'react';
import {getContractsByStatusRequest} from "../../api/Requests";
import Paginator from "../paging/Paginator";

export function TabContractsHistory(){
    const [contracts, setContracts] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    function ContractsTable({ contracts }) {
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
                    </tr>
                ))}
                </tbody>
            </table>
        );
    }

    useEffect(() => {
        const fetchContracts = async () => {
            const response = await getContractsByStatusRequest(page, 'DONE');
            setContracts(response.data.content);
            setTotalPages(response.data.totalPages);
        };
        fetchContracts();
    }, [page]);

    return (
        <div>
            <ContractsTable contracts={contracts} />
            <Paginator totalPages={totalPages} currentPage={page} onPageChange={setPage} />
        </div>
    );
}