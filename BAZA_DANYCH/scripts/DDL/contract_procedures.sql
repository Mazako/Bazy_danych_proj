CREATE OR REPLACE FUNCTION refund_factor(search_contract_id int8)
    RETURNS real
    LANGUAGE plpgsql
AS
$$
DECLARE
    tour_departure_date date;
    months_diff interval;
BEGIN
    SELECT t.departure_date
    into tour_departure_date
    FROM contract
             INNER JOIN tour t ON contract.tour_id = t.id
    WHERE contract.id = search_contract_id;

    months_diff := age(tour_departure_date, CURRENT_DATE);
    IF months_diff < INTERVAL '1 months' THEN
        RETURN 0.0;
    ELSIF months_diff < INTERVAL '2 months' THEN
        RETURN 0.5;
    ELSE
        RETURN 1.0;
    END IF;
END;
$$;


CREATE OR REPLACE FUNCTION get_refund_price(search_contract_id int8)
    RETURNS real
    LANGUAGE plpgsql
AS
$$
DECLARE
    refund_factor real;
    tour_price real;
BEGIN
    refund_factor := refund_factor(search_contract_id);
    IF (refund_factor = 0.0) IS TRUE THEN
        RETURN 0.0;
    END IF;
    SELECT t.price
    INTO tour_price
    FROM contract
             INNER JOIN public.tour t on contract.tour_id = t.id
    WHERE contract.id = search_contract_id;
    RETURN tour_price * refund_factor;
END;
$$;


CREATE OR REPLACE PROCEDURE update_done_contracts()
LANGUAGE plpgsql
AS
$$
BEGIN
    UPDATE contract
    SET status = 'DONE'
    FROM tour
    WHERE contract.tour_id = tour.id
    AND CURRENT_DATE - tour.return_date >= 0;
END;
$$