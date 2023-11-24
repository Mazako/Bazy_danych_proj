INSERT INTO app_user (name, last_name, mail, password_hash, type, creation_date, phone)
VALUES
    ('Jan', 'Kowalski', 'jan.kowalski@example.com', 'hash1234', 'USER', '2023-01-01', '123456789'),
    ('Anna', 'Nowak', 'anna.nowak@example.com', 'hash5678', 'USER', '2023-02-01', '987654321'),
    ('Admin', 'Adminowy', 'admin@tourpol.com', 'hash9012', 'ADMIN', '2023-03-01', '112233445');

INSERT INTO city (country, name, latitude, longitude)
VALUES
    ('Polska', 'Warszawa', '52.2297', '21.0122'),
    ('Polska', 'Kraków', '50.0647', '19.9450'),
    ('Polska', 'Gdańsk', '54.3520', '18.6466');


INSERT INTO address (street, building_number, city_id)
VALUES
    ('Marszałkowska', '10', 1),
    ('Floriańska', '15', 2),
    ('Długa', '20', 3);


INSERT INTO city (country, name, latitude, longitude)
VALUES
    ('Hiszpania', 'Ibiza', '38.9067', '1.4206'),
    ('Grecja', 'Santorini', '36.3932', '25.4615'),
    ('Brazylia', 'Rio de Janeiro', '22.9068', '43.1729'),
    ('Malediwy', 'Male', '4.1755', '73.5093');

INSERT INTO address (street, building_number, city_id)
VALUES
    ('Calle de Ibiza', '5', 1),
    ('Oia Street', '21', 2),
    ('Avenida Atlântica', '300', 3),
    ('Hulhumale Road', '10', 4);


INSERT INTO resort (name, address_id, avg_opinion)
VALUES
    ('Ibiza Sun Resort', 1, 4.5),
    ('Santorini Dreams', 2, 4.8),
    ('Rio Beachfront', 3, 4.3),
    ('Male Oceanview', 4, 4.7);


INSERT INTO facility (wifi, swimming_pool, air_conditioning, gym, food, room_service, bar, restaurant, free_parking, all_time_reception)
VALUES
    (true, true, true, true, true, true, true, true, true, true),
    (true, false, true, false, true, false, true, true, true, true),
    (false, true, true, true, false, true, false, true, false, true),
    (true, true, true, true, true, true, true, true, true, true);


INSERT INTO tour (name, price, departure_date, return_date, description, facility_id, resort_id)
VALUES
    ('Ibiza Adventure', 1200.0, '2023-06-15', '2023-06-22', 'Odkryj piękno Ibizy', 1, 1),
    ('Santorini Escape', 1500.0, '2023-07-01', '2023-07-08', 'Ciesz się słońcem Santorini', 2, 2),
    ('Rio Carnival', 1800.0, '2023-08-20', '2023-08-27', 'Doświadcz karnawału w Rio', 3, 3),
    ('Maldives Relax', 2000.0, '2023-09-10', '2023-09-17', 'Wypocznij na Malediwach', 4, 4);


INSERT INTO contract (reservation_date, status, pearson_count, user_id, tour_id)
VALUES
    ('2023-01-10', 'PAID', 2, 1, 1),
    ('2023-02-15', 'PENDING_PAYMENT', 4, 2, 2),
    ('2023-03-20', 'IN_PROGRESS', 1, 3, 3),
    ('2023-04-25', 'DONE', 3, 1, 4);


INSERT INTO notification_type (type)
VALUES
    ('Reservation Confirmation'),
    ('Payment Reminder'),
    ('Travel Alert'),
    ('Post-Trip Survey');

INSERT INTO notification (content, seen, send_date, contract_id, notification_type_id)
VALUES
    ('Twoja rezerwacja na Ibiza Adventure została potwierdzona!', false, '2023-01-11', 1, 1),
    ('Przypominamy o płatności za Santorini Escape', false, '2023-02-16', 2, 2),
    ('Ważne informacje dotyczące Twojej podróży do Rio', true, '2023-03-21', 3, 3),
    ('Jak oceniasz swój pobyt na Malediwach?', true, '2023-04-26', 4, 4);


INSERT INTO room (person_count, name, standard, resort_id)
VALUES
    (2, 'Standardowy Pokój Dwuosobowy', 2, 1),
    (2, 'Pokój z Widokiem na Morze', 3, 1),
    (4, 'Apartament Rodzinny', 3, 1),
    (1, 'Pokój Jednoosobowy', 1, 1);

-- Pokoje dla kurortu Ibiza Sun Resort
INSERT INTO room (person_count, name, standard, resort_id)
VALUES
    (3, 'Pokój Trzyosobowy Deluxe', 3, 2),
    (2, 'Pokój Dwuosobowy z Tarasem', 2, 2),
    (2, 'Pokój dla Nowożeńców', 3, 2),
    (4, 'Apartament z Jacuzzi', 3, 2);

-- Pokoje dla kurortu Santorini Dreams
INSERT INTO room (person_count, name, standard, resort_id)
VALUES
    (2, 'Pokój Dwuosobowy Standard', 2, 3),
    (3, 'Pokój Rodzinny', 2, 3),
    (2, 'Apartament Prezydencki', 3, 3),
    (1, 'Pokój Jednoosobowy z Widokiem na Morze', 2, 3);

-- Pokoje dla kurortu Rio Beachfront
INSERT INTO room (person_count, name, standard, resort_id)
VALUES
    (2, 'Bungalow na Wodzie', 3, 4),
    (2, 'Pokój Dwuosobowy z Prywatnym Basenem', 3, 4),
    (4, 'Rodzinny Apartament Plażowy', 3, 4),
    (1, 'Luksusowy Pokój Jednoosobowy', 2, 4);

-- Pokoje dla kurortu Male Oceanview
INSERT INTO room_tour(room_id, tour_id)
VALUES
    (8, 3);

INSERT INTO room_contract(room_id, contract_id)
VALUES
    (8, 3);
