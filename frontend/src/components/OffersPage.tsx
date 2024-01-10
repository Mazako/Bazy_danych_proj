import React, { useEffect, useState } from "react";
import { getResortsRequest } from "../api/Requests";
import {ResortItem} from "../features/resort/ResortType";
import Paginator from "./resorts/ResortPaginator";
import ResortCard from "./resorts/ResortCard";
import {Row} from "react-bootstrap";

export const OffersPage = () => {
    const [offers, setOffers] = useState<ResortItem[]>([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        const fetchResorts = async () => {
            try {
                const response = await getResortsRequest(page);
                if (response && response.data && response.data.content) {
                    setOffers(response.data.content);
                    setTotalPages(response.data.totalPages);
                }
            } catch (error) {
                console.error("Failed to fetch resorts:", error);
            }
        };
        fetchResorts();
    }, [page]);

    return (
        <div className="my-3">
            <Row xs={1} md={3} className="g-4">
                {offers && offers.map(offer => <ResortCard offer={offer} key={offer.resortName} />)}
            </Row>
            <div  className="my-4">
                <Paginator totalPages={totalPages} page={page} setPage={setPage} />
            </div>
        </div>
    );
};
