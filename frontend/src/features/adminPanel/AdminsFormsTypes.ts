import {Facility} from "../resort/ResortType";
import {RoomsResponse} from "../resort/RoomsType";

export interface NewTourDto{
    resortId: number,
    name: string,
    description: string,
    price: number,
    departureDate: Date,
    returnDate: Date,
    facilities: Facility,
    roomIds: number[]

}
export interface ResortDTO {
    name: string;
    description: string;
    address: AddressDTO;
    rooms: RoomsResponse[];
}
export interface CityDTO {
    country: string;
    name: string;
    latitude: string;
    longitude: string;
}

export interface AddressDTO {
    street: string;
    buildingNumber: string;
    houseNumber: string;
    city: CityDTO;
}