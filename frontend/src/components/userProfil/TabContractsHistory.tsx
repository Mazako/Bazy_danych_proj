import React, {useEffect, useState} from 'react';
import {checkIfOpinionIsAddedRequest, getContractsByStatusRequest} from "../../api/Requests";
import Paginator from "../paging/Paginator";
import OpinionModal from "./OpinionModal";

export function TabContractsHistory() {
    const [contracts, setContracts] = useState([]);
    const [opinionsAdded, setOpinionsAdded] = useState({});
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [showModal, setShowModal] = useState(false);
    const [selectedContractId, setSelectedContractId] = useState(null);


    const fetchContracts = async () => {
        const response = await getContractsByStatusRequest(page, 'DONE');
        setContracts(response.data.content);
        setTotalPages(response.data.totalPages);
        const opinionPromises = response.data.content.map(contract =>
            checkIfOpinionIsAddedRequest(contract.id)
        );
        const opinionsResults = await Promise.all(opinionPromises);
        const opinionsAddedMap = opinionsResults.reduce((acc, current, index) => {
            acc[response.data.content[index].id] = current.data;
            return acc;
        }, {});
        setOpinionsAdded(opinionsAddedMap);
    };
    useEffect(() => {
        fetchContracts();
    }, [page]);

    const handleShowModal = (contractId) => {
        setSelectedContractId(contractId);
        setShowModal(true);
    };

    const handleCloseModal = () => setShowModal(false);

    function ContractsTable({contracts}) {
        return (
            <table className="table table-hover table-striped">
                <thead>
                <tr>
                    <th>Kurort</th>
                    <th>Data</th>
                    <th>Miejsce</th>
                    <th>Ilość osób</th>
                    <th>Cena całkowita</th>
                    <th>Status</th>
                    <th>Opinia</th>
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
                            {}
                            {!opinionsAdded[contract.id] && (
                                <button
                                    className="btn btn-danger text-black"
                                    onClick={() => handleShowModal(contract.id)}>
                                    Dodaj opinię
                                </button>
                            )}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        );
    }

    return (
        <>
            <ContractsTable contracts={contracts}/>
            <Paginator totalPages={totalPages} currentPage={page} onPageChange={setPage}/>
            <OpinionModal
                show={showModal}
                handleClose={handleCloseModal}
                contractId={selectedContractId}
                refreshContracts={fetchContracts}
            />
        </>
    );
}