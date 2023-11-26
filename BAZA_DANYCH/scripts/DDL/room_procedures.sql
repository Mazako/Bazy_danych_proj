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
