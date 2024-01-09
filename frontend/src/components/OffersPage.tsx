import {useEffect, useState} from "react";
import {Offer} from "./Offer";

export const OffersPage = () => {
    const [offers, setOffers] = useState()
    const [page, setPage] = useState(1)

    useEffect(() => {
        console.log('xd')
        setPage(prevState => prevState + 1)
    }, [page])


    return (
        <div>
            <Offer name='mazak' price='99gr'></Offer>
        </div>
    )
}