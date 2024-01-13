INSERT INTO city
VALUES (1, 'Polska', 'Lądek-Zdrój', '21', '37');

INSERT INTO address
VALUES (1, 'Legnicka', '21', '37', 1);

INSERT INTO resort
VALUES (1, 'Tour', 1, 5, 'Description');

INSERT INTO tour
VALUES (1, 'T1', 1000, '2023-10-10', '2023-10-20', 'D', 1, 1),
       (2, 'T2', 1000, '2023-10-15', '2023-11-25', 'D', 1, 1);

INSERT INTO room
VALUES (1, 1, 'Room1', 1, 1),
       (2, 1, 'Room2', 1, 1),
       (3, 1, 'Room3', 1, 1);

INSERT INTO room_tour
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 3, 2);