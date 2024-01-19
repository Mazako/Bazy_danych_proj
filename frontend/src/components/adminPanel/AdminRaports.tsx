import React, {useEffect, useState} from "react";
import {PopularityReportDTO} from "../../features/adminPanel/AdminsFormsTypes";
import {Button, Col, Container, FormControl, InputGroup, Row} from 'react-bootstrap';

import {getPopularityReportRequest} from "../../api/Requests";
import '../AdminReports.css';
import Paginator from "../paging/Paginator";

export function AdminRaports(){
    const [page, setPage] = useState(0);
    const [reports, setReports] = useState<PopularityReportDTO[]>([]);
    const defaultStartDate = new Date();
    defaultStartDate.setFullYear(defaultStartDate.getFullYear() - 2);
    const defaultEndDate = new Date();
    const [startDate, setStartDate] = useState(defaultStartDate.toISOString().split('T')[0]);
    const [endDate, setEndDate] = useState(defaultEndDate.toISOString().split('T')[0]);

    const fetchPopularityReports = async () => {
        try {
            const response = await getPopularityReportRequest(page, 15, startDate, endDate);
            setReports(response.data);
        } catch (error) {
            console.error("Failed to fetch popularity reports:", error);
        }
    };
    useEffect(() => {
        fetchPopularityReports();
    }, [page, startDate, endDate]);

    const handleSearch = () => {
        fetchPopularityReports();
    };
    return (
        <Container fluid>
            <Row className="mb-4">
                <Col md={12}>
                    <InputGroup>
                        <InputGroup.Text>Od</InputGroup.Text>
                        <FormControl
                            type="date"
                            value={startDate || ''}
                            onChange={e => setStartDate(e.target.value)}
                        />
                        <InputGroup.Text>Do</InputGroup.Text>
                        <FormControl
                            type="date"
                            value={endDate || ''}
                            onChange={e => setEndDate(e.target.value)}
                        />
                        <Button variant="outline-secondary" onClick={handleSearch}>Search</Button>
                    </InputGroup>
                </Col>
            </Row>
        <Container fluid className="report-table">
            <Row className="report-header">
                <Col md={3}><strong>Nazwa kurortu</strong></Col>
                <Col md={3}><strong>Umowy</strong></Col>
                <Col md={3}><strong>Liczba osób</strong></Col>
                <Col md={3}><strong>Zysk</strong></Col>
            </Row>
            {reports.map(report => (
                <Row key={report.id} className="report-row">
                    <Col md={3}>{report.resortName}</Col>
                    <Col md={3}>{report.signedContracts}</Col>
                    <Col md={3}>{report.persons}</Col>
                    <Col md={3}>${report.totalProfit.toFixed(2)}</Col>
                </Row>
            ))}
            <Paginator totalPages={1} currentPage={page} onPageChange={setPage} />
        </Container>
        </Container>

    );
}