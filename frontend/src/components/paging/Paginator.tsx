import React from 'react';
import { Pagination } from 'react-bootstrap';

function Paginator({ totalPages, currentPage, onPageChange }) {
    let items = [];
    for (let number = 1; number <= totalPages; number++) {
        items.push(
            <Pagination.Item key={number} active={number === currentPage + 1} onClick={() => onPageChange(number - 1)}>
                {number}
            </Pagination.Item>,
        );
    }

    return (
        <div className="d-flex justify-content-center">
            <Pagination>{items}</Pagination>
        </div>
    );
}

export default Paginator;
