INSERT INTO app_user (name, last_name, mail, password_hash, role_id, creation_date, phone)
VALUES ('Jan', 'Kowalski', 'jan.kowalski@example.com', 'hash1234', 2, '2023-01-01', '123456789'),
       ('Anna', 'Nowak', 'anna.nowak@example.com', 'hash5678', 2, '2023-02-01', '987654321'),
       ('Admin', 'Adminowy', 'admin', '$2a$10$pu4f2.4uTFkKO2iGhcbkzOmQtxgf29sRURi9Rg1wDA5qQccqLjQ3S', 1, '2023-03-01',
        '112233445');

INSERT INTO city (country, name, latitude, longitude)
VALUES ('Polska', 'Warszawa', '52.2297', '21.0122'),
       ('Polska', 'Kraków', '50.0647', '19.9450'),
       ('Polska', 'Gdańsk', '54.3520', '18.6466'),
       ('Hiszpania', 'Ibiza', '38.9067', '1.4206'),
       ('Grecja', 'Santorini', '36.3932', '25.4615'),
       ('Brazylia', 'Rio de Janeiro', '22.9068', '43.1729'),
       ('Malediwy', 'Male', '4.1755', '73.5093'),
       ('Włochy', 'Rzym', '41.9028', '12.4964'),
       ('Francja', 'Paryż', '48.8566', '2.3522');

INSERT INTO address (street, building_number, city_id)
VALUES ('Marszałkowska', '10', 1),
       ('Floriańska', '15', 2),
       ('Długa', '20', 3),
       ('Via Roma', '100', 4),
       ('Champs-Élysées', '50', 5),
       ('Tahrir Square', '25', 6),
       ('Wall Street', '12', 7),
       ('Calle de Ibiza', '5', 8),
       ('Oia Street', '21', 9);



INSERT INTO resort (name, address_id, avg_opinion)
VALUES ('Ibiza Sun Resort', 1, null),
       ('Santorini Dreams', 2, null),
       ('Rio Beachfront', 3, null),
       ('Male Oceanview', 4, null),
       ('Tropical Paradise', 5, null),
       ('Mountain Retreat', 6, null),
       ('Urban Hotel', 7, null),
       ('Seaside Resort', 8, null),
       ('Forest Lodge', 9, null);


INSERT INTO facility (wifi, swimming_pool, air_conditioning, gym, food, room_service, bar, restaurant, free_parking,
                      all_time_reception)
VALUES (true, true, true, true, true, true, true, true, true, true),
       (true, false, true, false, true, false, true, true, true, true),
       (false, true, true, true, false, true, false, true, false, true);


INSERT INTO tour (name, price, departure_date, return_date, description, facility_id, resort_id)
VALUES ('Ibiza Adventure', 1200.0, '2023-06-15', '2023-06-22', 'Odkryj piękno Ibizy', 1, 1),
       ('Santorini Escape', 1500.0, '2023-07-01', '2023-07-08', 'Ciesz się słońcem Santorini', 2, 2),
       ('Rio Carnival', 1800.0, '2023-08-20', '2023-08-27', 'Doświadcz karnawału w Rio', 3, 3),
       ('Maldives Relax', 2000.0, '2023-09-10', '2023-09-17', 'Wypocznij na Malediwach', 1, 4),
       ('Springtime in Paris', 1300.0, '2024-04-10', '2024-04-17', 'Wiosenny Paryż', 2, 5),
       ('Autumn in New York', 1500.0, '2024-10-05', '2024-10-12', 'Jesień w Nowym Jorku', 3, 6),
       ('Summer in Santorini', 1700.0, '2024-07-15', '2024-07-22', 'Letni wypoczynek na Santorini', 1, 7),
       ('Winter in Tokyo', 2000.0, '2024-12-20', '2024-12-27', 'Zimowa przygoda w Tokio', 3, 8),
       ('Discover London', 1400.0, '2024-06-01', '2024-06-08', 'Odkryj Londyn', 2, 9),
       ('Caribbean Dream', 1800.0, '2024-05-20', '2024-05-27', 'Karaibski Sen', 1, 1),
       ('Alps Ski Adventure', 1600.0, '2024-02-10', '2024-02-17', 'Narciarska przygoda w Alpach', 2, 1),
       ('Cruise in Mediterranean', 1900.0, '2024-08-15', '2024-08-22', 'Rejs po Morzu Śródziemnym', 1, 1),
       ('African Safari', 2100.0, '2024-09-10', '2024-09-17', 'Safari w Afryce', 3, 2),
       ('Thai Beach Escape', 1600.0, '2024-11-12', '2024-11-19', 'Tajskie Plaże', 1, 2),
       ('Australian Outback', 2000.0, '2024-03-05', '2024-03-12', 'Przygoda w australijskim Outbacku', 2, 2),
       ('Iceland Northern Lights', 1800.0, '2024-01-15', '2024-01-22', 'Islandzkie Zorze Polarne', 3, 3);


INSERT INTO contract (reservation_date, status, pearson_count, user_id, tour_id)
VALUES ('2023-01-10', 'PAID', 2, 1, 1),
       ('2023-02-15', 'PENDING_PAYMENT', 4, 2, 2),
       ('2023-03-20', 'IN_PROGRESS', 1, 3, 3),
       ('2023-04-25', 'DONE', 3, 1, 4);


INSERT INTO notification_type (type)
VALUES ('Reservation Confirmation'),
       ('Payment Reminder'),
       ('Travel Alert'),
       ('Post-Trip Survey');

INSERT INTO notification (content, seen, send_date, contract_id, notification_type_id)
VALUES ('Twoja rezerwacja na Ibiza Adventure została potwierdzona!', false, '2023-01-11', 1, 1),
       ('Przypominamy o płatności za Santorini Escape', false, '2023-02-16', 2, 2),
       ('Ważne informacje dotyczące Twojej podróży do Rio', true, '2023-03-21', 3, 3),
       ('Jak oceniasz swój pobyt na Malediwach?', true, '2023-04-26', 4, 4);


INSERT INTO room (person_count, name, standard, resort_id)
VALUES (2, 'Standardowy Pokój Dwuosobowy', 2, 1),
       (2, 'Pokój z Widokiem na Morze', 3, 1),
       (4, 'Apartament Rodzinny', 3, 1),
       (1, 'Pokój Jednoosobowy', 1, 1);

INSERT INTO room (person_count, name, standard, resort_id)
VALUES (3, 'Pokój Trzyosobowy Deluxe', 3, 2),
       (2, 'Pokój Dwuosobowy z Tarasem', 2, 2),
       (2, 'Pokój dla Nowożeńców', 3, 2),
       (4, 'Apartament z Jacuzzi', 3, 2);

INSERT INTO room (person_count, name, standard, resort_id)
VALUES (2, 'Pokój Dwuosobowy Standard', 2, 3),
       (3, 'Pokój Rodzinny', 2, 3),
       (2, 'Apartament Prezydencki', 3, 3),
       (1, 'Pokój Jednoosobowy z Widokiem na Morze', 2, 3);

INSERT INTO room (person_count, name, standard, resort_id)
VALUES (2, 'Bungalow na Wodzie', 3, 4),
       (2, 'Pokój Dwuosobowy z Prywatnym Basenem', 3, 4),
       (4, 'Rodzinny Apartament Plażowy', 3, 4),
       (1, 'Luksusowy Pokój Jednoosobowy', 2, 4);

INSERT INTO room (person_count, name, standard, resort_id)
VALUES (2, 'Deluxe Room', 3, 5),
       (1, 'Single Economy', 2, 5),
       (4, 'Family Suite', 1, 5);

INSERT INTO room (person_count, name, standard, resort_id)
VALUES (3, 'Mountain View', 3, 6),
       (2, 'Cozy Double', 2, 6),
       (1, 'Solo Adventure', 2, 6);

INSERT INTO room (person_count, name, standard, resort_id)
VALUES (2, 'City Double', 3, 7),
       (3, 'Triple Suite', 1, 7),
       (1, 'Compact Single', 2, 7);

INSERT INTO room (person_count, name, standard, resort_id)
VALUES (2, 'Sea Breeze Double', 3, 8),
       (4, 'Beachfront Family', 1, 8),
       (1, 'Single Sea View', 2, 8);

INSERT INTO room (person_count, name, standard, resort_id)
VALUES (2, 'Forest Double', 3, 9),
       (3, 'Nature Triple', 2, 9),
       (1, 'Single Eco', 2, 9);



INSERT INTO room_tour(room_id, tour_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1);

INSERT INTO room_tour(room_id, tour_id)
VALUES (5, 2),
       (6, 2),
       (7, 2),
       (8, 2);

INSERT INTO room_tour(room_id, tour_id)
VALUES (9, 3),
       (10, 3),
       (11, 3),
       (12, 3);

INSERT INTO room_tour(room_id, tour_id)
VALUES (13, 4),
       (14, 4),
       (15, 4),
       (16, 4);

INSERT INTO room_tour(room_id, tour_id)
VALUES (17, 5),
       (18, 5),
       (19, 5);

INSERT INTO room_tour(room_id, tour_id)
VALUES (20, 6),
       (21, 6),
       (22, 6);

INSERT INTO room_tour(room_id, tour_id)
VALUES (23, 7),
       (24, 7),
       (25, 7);

INSERT INTO room_tour(room_id, tour_id)
VALUES (26, 8),
       (27, 8),
       (28, 8);



INSERT INTO room_tour(room_id, tour_id)
VALUES (29, 9),
       (30, 9),
       (31, 9);


INSERT INTO room_contract(room_id, contract_id)
VALUES (9, 3);
