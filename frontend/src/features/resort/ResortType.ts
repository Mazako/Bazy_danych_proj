export interface ResortItem {
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
