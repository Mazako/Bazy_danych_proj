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


