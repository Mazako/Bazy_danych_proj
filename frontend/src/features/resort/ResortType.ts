export interface ResortItem {
    id: number
    resortName: string,
    averageOpinion: number,
    country: string,
    city: string,
    price: number,
    departureData: string,
    returnDate: string
    latitude: string
    longitude: string

}
export interface ResortResponse{
    id:number
    resortName: string,
    averageOpinion: number,
    description: string,
    country: string,
    city: string,
    latitude: string
    longitude: string
}

export interface ResortsListResponse {
    content: ResortItem[];
    totalPages: number;
    totalElements: number;
}
export interface SearchParams {
    resortName?: string;
    country?: string;
    city?: string;
    minPrice?: number;
    maxPrice?: number;
    departureDate?: string;
    returnDate?: string;
}



export interface ToursListResponse {
    content: TourDTO[];
    totalPages: number;
    totalElements: number;
}

export interface Facility {
    wifi: boolean;
    swimmingPool: boolean;
    airConditioning: boolean;
    gym: boolean;
    food: boolean;
    roomService: boolean;
    bar: boolean;
    restaurant: boolean;
    freeParking: boolean;
    allTimeReception: boolean;
}

export interface TourDTO {
    id: number
    facilities: Facility;
    name: string;
    description: string;
    price: number;
    departureDate: string;
    returnDate: string;
    roomsCount: number;
    placesLeft: number;
}
