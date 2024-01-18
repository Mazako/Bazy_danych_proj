import React, { useEffect, useState } from "react";
import {getResortsRequest, searchResortsRequest} from "../api/Requests";
import {ResortItem, SearchParams} from "../features/resort/ResortType";
import Paginator from "./resorts/ResortPaginator";
import ResortCard from "./resorts/ResortCard";
import {Row} from "react-bootstrap";
import {SearchBar} from "./resorts/ResortSearchBar";
import {useLocation} from "react-router";
import ToursPage from "./ToursPage";

export const OffersPage = () => {
    const [offers, setOffers] = useState<ResortItem[]>([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [isSearchMode, setIsSearchMode] = useState(false);
    const [searchParams, setSearchFilters] = useState<SearchParams | null>(null);

    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const id = queryParams.get('id');


    const handleSearch = async (filters: SearchParams) => {
        setSearchFilters(filters);
        setIsSearchMode(true);
        setPage(0);
        setOffers([]);
    };

    const resetSearch = () => {
        setIsSearchMode(false);
        setSearchFilters(null);
        setPage(0);
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                let response;
                if (isSearchMode && searchParams) {
                    response = await searchResortsRequest({ ...searchParams }, page);
                } else {
                    response = await getResortsRequest(page);
                    console.log(response);
                }

                if (response && response.data && response.data.content) {
                    setOffers(response.data.content);
                    setTotalPages(response.data.totalPages);
                }
            } catch (error) {
                console.error("Failed to fetch data:", error);
            }
        };

        fetchData();
    }, [page, isSearchMode, searchParams]);

    if (id) {
        return <ToursPage />;
    }

    return (
        <div className="my-3">
            <SearchBar onSearch={handleSearch} onReset={resetSearch} />
            <Row xs={1} md={3} className="g-4 mt-3" style={{ marginLeft: '5rem', marginRight: '5rem' }}>
                {offers && offers.map(offer => <ResortCard offer={offer} key={offer.resortName} />)}
            </Row>
            <div  className="my-4">
                <Paginator totalPages={totalPages} page={page} setPage={setPage} />
            </div>
        </div>
    );
};
