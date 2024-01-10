import {Pagination} from "react-bootstrap";
import React from "react";

const Paginator = ({ totalPages, page, setPage }) => {
    const renderPaginationItems = () => {
        let items = [];
        for (let number = 1; number <= totalPages; number++) {
            items.push(
                <Pagination.Item key={number} active={number === page + 1} onClick={() => setPage(number - 1)}>
                    {number}
                </Pagination.Item>
            );
        }
        return items;
    };
    return (
        <div className="d-flex justify-content-center my-7">
            <Pagination>{renderPaginationItems()}</Pagination>
        </div>
    );
};
export default Paginator;