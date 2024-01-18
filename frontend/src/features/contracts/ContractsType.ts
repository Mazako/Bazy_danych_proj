import {RoomsResponse} from "../resort/RoomsType";
import {ResortItem} from "../resort/ResortType";

export interface ContractDTO {
    id: number;
    resortName: string;
    departureDate: string;
    returnDate: string;
    country: string;
    city: string;
    reservationDate: string;
    status: string;
    personCount: number;
    totalPrice: number;
    rooms: RoomsResponse[];
}
export interface ContractDTOList{
    content: ContractDTO[];
    totalPages: number;
    totalElements: number;
}