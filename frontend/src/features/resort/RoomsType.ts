export interface RoomsResponse{
    id: number
    name: string
    personCount: number
    standard: number
}

export interface CreateContractDto{
    tourId: number
    roomIds: number[]
}

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

