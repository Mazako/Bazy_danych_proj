CREATE OR REPLACE FUNCTION is_room_available(search_room_id int8, start_date date, end_date date)
RETURNS bool
LANGUAGE plpgsql
AS
$$
DECLARE room_count int4;
BEGIN
    SELECT COUNT(room.id)
    INTO room_count
    FROM room
    INNER JOIN room_tour ON room.id = room_tour.room_id
    INNER JOIN tour ON room_tour.tour_id = tour.id
    WHERE (search_room_id = room.id)
        AND ((start_date BETWEEN tour.departure_date AND tour.return_date)
        OR (end_date BETWEEN tour.departure_date AND tour.return_date)
        OR (start_date <= tour.departure_date AND end_date >= tour.return_date));
RETURN (room_count = 0) IS TRUE;
END;
$$;


CREATE OR REPLACE FUNCTION get_available_rooms(
    search_resort_id int8,
    start_date date,
    end_date date
)
    RETURNS SETOF room
    LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
        SELECT DISTINCT room.*
        FROM resort
                 INNER JOIN room ON resort.id = room.resort_id
                 LEFT JOIN room_tour ON room.id = room_tour.room_id
                 LEFT JOIN tour ON room_tour.tour_id = tour.id
        WHERE resort.id = search_resort_id
          AND (tour.id IS NULL OR
               (tour.departure_date IS NULL OR tour.return_date IS NULL OR
                (NOT (start_date BETWEEN tour.departure_date AND tour.return_date) AND
                 NOT (end_date BETWEEN tour.departure_date AND tour.return_date) AND
                 NOT (start_date <= tour.departure_date AND end_date >= tour.return_date))));
END;
$$;


CREATE OR REPLACE FUNCTION check_room_availability()
    RETURNS TRIGGER AS $$
DECLARE
    tourStartDate DATE;
    tourEndDate DATE;
    tourId INT8;
    isRoomOccupied BOOL;
BEGIN
    SELECT t.departure_date, t.return_date, t.id INTO tourStartDate, tourEndDate, tourId
    FROM contract c
             JOIN tour t ON c.tour_id = t.id
    WHERE c.id = NEW.contract_id;

    IF NOT EXISTS (
            SELECT 1
            FROM room_tour rt
            WHERE rt.room_id = NEW.room_id AND rt.tour_id = (SELECT tour_id FROM contract WHERE id = NEW.contract_id)
        ) THEN
        RAISE EXCEPTION 'Pokój nie jest częścią wycieczki dla tej umowy!';
    END IF;

    SELECT NEW.room_id IN (
        SELECT room_id
        FROM room_contract
        INNER JOIN contract ON room_contract.contract_id = contract.id
        WHERE contract.tour_id = tourId) INTO isRoomOccupied;

    IF (isRoomOccupied) THEN
        RAISE EXCEPTION 'Pokój jest już zajęty w tym terminie!';
    end if;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_room_availability
    BEFORE INSERT OR UPDATE ON room_contract
    FOR EACH ROW EXECUTE FUNCTION check_room_availability();



CREATE OR REPLACE FUNCTION check_room_before_insert_or_update()
    RETURNS TRIGGER AS $$
DECLARE
    isAvailable BOOL;

BEGIN
    IF NOT EXISTS (
            SELECT 1
            FROM room
                     JOIN tour ON room.resort_id = tour.resort_id
            WHERE room.id = NEW.room_id AND tour.id = NEW.tour_id
        ) THEN
        RAISE EXCEPTION 'Pokój nie znajduje się w odpowiednim kurorcie!';
    END IF;

    isAvailable := is_room_available(NEW.room_id,
        (SELECT departure_date FROM tour WHERE id = NEW.tour_id),
        (SELECT return_date FROM tour WHERE id = NEW.tour_id));

    IF NOT isAvailable THEN
        RAISE EXCEPTION 'Pokój nie jest dostępny w tym okresie!';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_room_before_insert_or_update
    BEFORE INSERT OR UPDATE ON room_tour
    FOR EACH ROW EXECUTE FUNCTION check_room_before_insert_or_update();


create function generate_popularity_report(
    start_date date,
    end_date date,
    page_offset int,
    page_limit int)
    RETURNS TABLE (id int8, resort_name varchar(100), signed_contracts int8, persons int8, total_profit double precision)
    language plpgsql
as
$$
BEGIN
    RETURN QUERY
        SELECT resort.id,
               resort.name AS resort_name,
               COUNT(contract.id) AS signed_contracts,
               SUM(contract.pearson_count) AS persons,
               SUM(tour.price * contract.pearson_count) AS total_profit

        FROM resort
                 LEFT JOIN tour ON resort.id = tour.resort_id
                 LEFT JOIN contract ON tour.id = contract.tour_id
        WHERE contract.reservation_date BETWEEN start_date AND end_date
        GROUP BY resort.id
        HAVING COUNT(contract.id) > 0
        ORDER BY resort.id
        OFFSET page_offset ROWS
            FETCH FIRST page_limit ROWS ONLY;
END;
$$;
