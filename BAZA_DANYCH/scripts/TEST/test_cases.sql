--check_room_before_insert_or_update
DO $$
    DECLARE
        tour_id1 INT;
        tour_id2 INT;
        room_id INT;
    BEGIN
        INSERT INTO tour (name, price, departure_date, return_date, description, facility_id, resort_id)
        VALUES ('Wycieczka w Tym Samym Kurorcie', 1000.00, '2023-06-01', '2023-06-07', 'Opis Wycieczki 1', 1, 1) RETURNING id INTO tour_id1;
        INSERT INTO tour (name, price, departure_date, return_date, description, facility_id, resort_id)
        VALUES ('Wycieczka w Innym Kurorcie', 1500.00, '2023-07-01', '2023-07-07', 'Opis Wycieczki 2', 1, 2) RETURNING id INTO tour_id2;

        INSERT INTO room (person_count, name, standard, resort_id)
        VALUES (2, 'Pokój Testowy', 2, 1) RETURNING id INTO room_id;


        INSERT INTO room_tour (room_id, tour_id) VALUES (room_id, tour_id1);
--error
        INSERT INTO room_tour (room_id, tour_id) VALUES (room_id, tour_id2);

    END $$;



--available rooms
DO $$
    DECLARE
        contract_id1 INT;
        contract_id2 INT;
        tour_id1 INT;
        tour_id2 INT;
        room_id INT;
    BEGIN
        INSERT INTO tour (name, price, departure_date, return_date, description, facility_id, resort_id)
        VALUES ('Wycieczka 1', 1000.00, '2023-02-01', '2023-02-07', 'Opis Wycieczki 1', 1, 1) RETURNING id INTO tour_id1;
        INSERT INTO tour (name, price, departure_date, return_date, description, facility_id, resort_id)
        VALUES ('Wycieczka 2', 1500.00, '2023-02-04', '2023-02-10', 'Opis Wycieczki 2', 1, 1) RETURNING id INTO tour_id2;

        INSERT INTO room (person_count, name, standard, resort_id)
        VALUES (2, 'Standardowy Pokój Dwuosobowy', 2, 1) RETURNING id INTO room_id;

        INSERT INTO contract (reservation_date, status, pearson_count, user_id, tour_id)
        VALUES ('2023-01-01', 'PAID', 2, 1, tour_id1) RETURNING id INTO contract_id1;
        INSERT INTO contract (reservation_date, status, pearson_count, user_id, tour_id)
        VALUES ('2023-01-15', 'PAID', 2, 1, tour_id2) RETURNING id INTO contract_id2;

        INSERT INTO room_tour (room_id, tour_id) VALUES (room_id, tour_id1), (room_id, tour_id2);

        INSERT INTO room_contract (room_id, contract_id) VALUES (room_id, contract_id1);

        INSERT INTO room_contract (room_id, contract_id) VALUES (room_id, contract_id2);

    END $$;


--check_reservation_before_departure
DO $$
    DECLARE
        tour_id1 int8;
        tour_id2 int8;
    BEGIN
        INSERT INTO tour (name, price, departure_date, return_date, description, facility_id, resort_id)
        VALUES ('Testowa Wycieczka 1', 1000, '2023-07-10', '2023-07-20', 'Opis...', 1, 1)
        RETURNING id INTO tour_id1;

        INSERT INTO tour (name, price, departure_date, return_date, description, facility_id, resort_id)
        VALUES ('Testowa Wycieczka 2', 2000, '2023-08-15', '2023-08-25', 'Opis...', 1, 1)
        RETURNING id INTO tour_id2;

        INSERT INTO contract (reservation_date, status, pearson_count, user_id, tour_id)
        VALUES ('2023-07-12', 'PENDING_PAYMENT', 2, 1, tour_id2);

    END $$;
select * from contract;
--EROOR
DO $$
    DECLARE
        tour_id1 int8;
        tour_id2 int8;
    BEGIN
        INSERT INTO tour (name, price, departure_date, return_date, description, facility_id, resort_id)
        VALUES ('Testowa Wycieczka 1', 1000, '2023-07-10', '2023-07-20', 'Opis...', 1, 1)
        RETURNING id INTO tour_id1;

        INSERT INTO tour (name, price, departure_date, return_date, description, facility_id, resort_id)
        VALUES ('Testowa Wycieczka 2', 2000, '2023-08-15', '2023-08-25', 'Opis...', 1, 1)
        RETURNING id INTO tour_id2;

        INSERT INTO contract (reservation_date, status, pearson_count, user_id, tour_id)
        VALUES ('2024-07-01', 'PENDING_PAYMENT', 2, 1, tour_id1);
    END $$;


--Zliczanie sredniej opini
DO $$
    DECLARE
        resort_id int8;
        tour_id int8;
        contract_id int8;
    BEGIN
        INSERT INTO resort (name, address_id, avg_opinion)
        VALUES ('Resort Testowy', 1, 5.0)
        RETURNING id INTO resort_id;

        INSERT INTO tour (name, price, departure_date, return_date, description, facility_id, resort_id)
        VALUES ('Tour Testowy', 1000, '2023-01-01', '2023-01-10', 'Opis Touru', 1, resort_id)
        RETURNING id INTO tour_id;

        INSERT INTO contract (reservation_date, status, pearson_count, user_id, tour_id)
        VALUES ('2022-01-01', 'PAID', 2, 1, tour_id)
        RETURNING id INTO contract_id;

        INSERT INTO opinion (rate, comment, send_date, contract_id)
        VALUES (5, 'Świetny resort!', '2023-01-02', contract_id);
    END $$;

