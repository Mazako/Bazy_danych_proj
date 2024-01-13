CREATE OR REPLACE VIEW full_tour_info
AS
SELECT tour.id, tour.name AS name, tour.price, tour.departure_date, tour.return_date, tour.description,
       wifi, swimming_pool, air_conditioning, gym, food, room_service, bar, restaurant, free_parking, all_time_reception,
       resort_id
FROM tour
         INNER JOIN facility ON tour.facility_id = facility.id
         INNER JOIN resort ON tour.resort_id = resort.id;

CREATE OR REPLACE VIEW full_address_info
AS
SELECT address.id, address.street, address.building_number, address.house_number,
       city.country, city.name, city.latitude, city.longitude
FROM address
         INNER JOIN city ON address.city_id = city.id;

CREATE OR REPLACE VIEW full_resort_info
AS
SELECT resort.id, resort.name, resort.avg_opinion, resort.description,
       address.street, address.building_number, address.house_number,
       city.country, city.name AS city, city.longitude, city.latitude
FROM resort
         INNER JOIN address ON resort.address_id = address.id
         INNER JOIN city on address.city_id = city.id;

CREATE OR REPLACE VIEW full_notification_info
AS
SELECT notification.content, notification.send_date, notification.seen,
       notification_type.type,
       app_user.name, app_user.last_name, app_user.mail
FROM notification
         INNER JOIN notification_type ON notification.notification_type_id = notification_type.id
         INNER JOIN contract ON notification.contract_id = contract.id
         INNER JOIN app_user ON contract.user_id = app_user.id;

CREATE OR REPLACE VIEW full_opinion_info
AS
SELECT resort.id, resort.name,
       opinion.id AS opinion_id, opinion.send_date, opinion.comment, opinion.rate,
       app_user.name AS user_name, app_user.last_name
FROM resort
         INNER JOIN tour ON resort.id = tour.resort_id
         INNER JOIN contract ON tour.id = contract.tour_id
         INNER JOIN opinion ON contract.id = opinion.contract_id
         INNER JOIN app_user ON contract.user_id = app_user.id;

CREATE OR REPLACE VIEW regular_users
AS
SELECT * FROM app_user
WHERE role_id = 2;

CREATE OR REPLACE VIEW admins
AS
SELECT * FROM app_user
WHERE role_id = 1;

CREATE OR REPLACE VIEW incoming_tours
AS
SELECT *
FROM full_tour_info
WHERE departure_date > current_date AND return_date > current_date;

CREATE OR REPLACE VIEW ongoing_tours
AS
SELECT *
FROM full_tour_info
WHERE current_date BETWEEN departure_date AND return_date;

CREATE OR REPLACE VIEW done_tours
AS
SELECT *
FROM full_tour_info
WHERE return_date < current_date;