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
$$;


CREATE OR REPLACE FUNCTION update_resort_avg_opinion()
    RETURNS TRIGGER AS $$
DECLARE
    resort_id_to_update int8;
    new_avg_opinion REAL;
BEGIN
    SELECT tour.resort_id INTO resort_id_to_update
    FROM opinion
             JOIN contract ON opinion.contract_id = contract.id
             JOIN tour ON contract.tour_id = tour.id
    WHERE opinion.id = COALESCE(NEW.id, OLD.id);

    SELECT AVG(o.rate) INTO new_avg_opinion
    FROM opinion o
             JOIN contract c ON o.contract_id = c.id
             JOIN tour t ON c.tour_id = t.id
    WHERE t.resort_id = resort_id_to_update;

    UPDATE resort
    SET avg_opinion = new_avg_opinion
    WHERE id = resort_id_to_update;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION check_reservation_before_departure()
    RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT departure_date FROM tour WHERE id = NEW.tour_id) <= NEW.reservation_date THEN
        RAISE EXCEPTION 'Data rezerwacji musi być przed datą wyjazdu!';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
