CREATE OR REPLACE FUNCTION is_room_available(search_room_id int8, start_date date, end_date date)
RETURNS bool
LANGUAGE plpgsql
as
$$
    DECLARE room_count int4;
    BEGIN
        SELECT COUNT(room.id)
        INTO room_count
        FROM room
        INNER JOIN room_tour ON room.id = room_tour.room_id
        INNER JOIN tour ON room_tour.tour_id = tour.id
        WHERE (search_room_id = room.id)
            AND (start_date BETWEEN tour.departure_date AND tour.return_date)
            OR (end_date BETWEEN tour.departure_date AND tour.return_date)
            OR (start_date <= tour.departure_date AND end_date >= tour.return_date);
    RETURN (room_count = 0) IS TRUE;
    END;
$$