-- Basic DDL script for create tables, indexes and relations
-- enums
CREATE TYPE contract_status AS ENUM('PENDING_PAYMENT', 'CANCELLED', 'PAID', 'IN_PROGRESS', 'DONE');
-- tables

CREATE TABLE role (
    id          SERIAL8 NOT NULL,
    role_name   varchar(30) NOT NULL,
    PRIMARY KEY (id));

INSERT INTO role VALUES (1, 'ADMIN'), (2, 'USER'), (3, 'GUEST');

CREATE TABLE permission (
    id                  SERIAL8 NOT NULL,
    permission_name     varchar(100) NOT NULL,
    PRIMARY KEY (id));

INSERT INTO permission (permission_name) VALUES
    ('USERS_CREATE'), ('USERS_READ'), ('USERS_UPDATE'), ('USERS_DELETE'),
    ('CONTRACT_READ'), ('CONTRACT_MANAGE'),
    ('OPINION_CREATE'), ('OPINION_READ'), ('OPINION_UPDATE'), ('OPINION_DELETE'),
    ('NOTIFICATION_CREATE'), ('NOTIFICATION_READ'), ('NOTIFICATION_UPDATE'), ('NOTIFICATION_DELETE'),
    ('ROOM_CONTRACT_READ'), ('ROOM_CONTRACT_MANAGE'),
    ('ROOM_TOUR_READ'), ('ROOM_TOUR_MANAGE'),
    ('ROOM_READ'), ('ROOM_MANAGE'),
    ('FACILITY_READ'), ('FACILITY_MANAGE'),
    ('RESORT_READ'), ('RESORT_MANAGE'),
    ('ADDRESS_READ'), ('ADDRESS_MANAGE'),
    ('CITY_READ'), ('CITY_MANAGE'),
    ('TOUR_READ'), ('TOUR_MANAGE'),
    ('ROLE_READ'), ('ROLE_MANAGE'),
    ('PERMISSION_READ'), ('PERMISSION_MANAGE'),
    ('ROLE_PERMISSION_READ'), ('ROLE_PERMISSION_MANAGE');
--60

CREATE TABLE role_permission (
    id              SERIAL8 NOT NULL,
    role_id         int8 NOT NULL,
    permission_id   int8 NOT NULL,
    only_related    boolean NOT NULL DEFAULT false,
    PRIMARY KEY (id));

CREATE TABLE contract (
  id                SERIAL8 NOT NULL,
  reservation_date  date NOT NULL,
  status            contract_status NOT NULL DEFAULT 'PENDING_PAYMENT',
  pearson_count     int2 NOT NULL CHECK (pearson_count > 0),
  user_id           int8 NOT NULL,
  tour_id           int8 NOT NULL,
  PRIMARY KEY (id));
CREATE TABLE notification (
  id                   SERIAL8 NOT NULL,
  content              varchar(100) NOT NULL,
  seen                 bool NOT NULL,
  send_date            date NOT NULL,
  contract_id          int8 NOT NULL,
  notification_type_id int8,
  PRIMARY KEY (id));
CREATE TABLE notification_type (
    id SERIAL8 NOT NULL,
    type varchar(35) NOT NULL UNIQUE,
    PRIMARY KEY (id));
CREATE TABLE tour (
  id             SERIAL8 NOT NULL,
  name           varchar(255) NOT NULL,
  price          real NOT NULL CHECK ( price > 0.0 ),
  departure_date date NOT NULL,
  return_date    date NOT NULL CHECK (return_date > departure_date),
  description    varchar(1000),
  facility_id    int8 NOT NULL,
  resort_id      int8 NOT NULL,
  PRIMARY KEY (id));
CREATE TABLE resort (
  id         SERIAL8 NOT NULL,
  name       varchar(100) NOT NULL,
  address_id int8 NOT NULL,
  avg_opinion real,
  description varchar(1000),
  PRIMARY KEY (id));
CREATE TABLE app_user (
  id                SERIAL8 NOT NULL,
  name              varchar(20) NOT NULL,
  last_name         varchar(50) NOT NULL,
  mail              varchar(255) NOT NULL,
  password_hash     char(60) NOT NULL,
  role_id           int8,
  creation_date     date NOT NULL,
  phone             varchar(11),
  PRIMARY KEY (id));
CREATE TABLE opinion (
  id              SERIAL8 NOT NULL,
  rate            int2 NOT NULL CHECK ( rate BETWEEN 0 AND 5),
  comment         varchar(1000),
  send_date       date NOT NULL,
  contract_id     int8 UNIQUE ,
  PRIMARY KEY (id));
CREATE TABLE address (
  id                  SERIAL8 NOT NULL,
  street              varchar(100) NOT NULL,
  building_number     varchar(15) NOT NULL,
  house_number        varchar(15),
  city_id             int8 NOT NULL,
  PRIMARY KEY (id));
CREATE TABLE city (
  id               SERIAL8 NOT NULL,
  country          varchar(30) NOT NULL,
  name             varchar(50) NOT NULL,
  latitude         varchar(12),
  longitude        varchar(12),
  PRIMARY KEY (id));
CREATE TABLE facility (
  id                  SERIAL8 NOT NULL,
  wifi                bool NOT NULL,
  swimming_pool       bool NOT NULL,
  air_conditioning    bool NOT NULL,
  gym                 bool NOT NULL,
  food                bool NOT NULL,
  room_service        bool NOT NULL,
  bar                 bool NOT NULL,
  restaurant          bool NOT NULL,
  free_parking        bool NOT NULL,
  all_time_reception  bool NOT NULL,
  PRIMARY KEY (ID));
CREATE TABLE room (
  id            SERIAL8 NOT NULL,
  person_count  int2 NOT NULL CHECK ( person_count > 0 ),
  name          varchar(255) NOT NULL,
  standard      int2 NOT NULL CHECK ( standard BETWEEN 0 AND 3),
  resort_id     int8 NOT NULL,
  PRIMARY KEY (ID));
CREATE TABLE room_tour (
  id          SERIAL8 NOT NULL,
  room_id     int8 NOT NULL,
  tour_id     int8 NOT NULL,
  PRIMARY KEY (ID));
CREATE TABLE room_contract (
  id                SERIAL8 NOT NULL,
  room_id           int8 NOT NULL,
  contract_id       int8 NOT NULL,
  PRIMARY KEY (ID));

-- INDEXES
CREATE UNIQUE INDEX contract_id
  ON contract (id);
CREATE INDEX contract_reservation_date
  ON contract (reservation_date);
CREATE INDEX contract_user_id
  ON contract (user_id);
CREATE INDEX contract_tour_id
  ON contract (tour_id);

CREATE UNIQUE INDEX notification_id
  ON notification (id);
CREATE INDEX notification_contract_id
  ON notification (contract_id);
CREATE INDEX notification_type_id
  ON notification (notification_type_id);

CREATE UNIQUE INDEX notification_type_id_ind
    ON notification_type (id);

CREATE UNIQUE INDEX tour_id
  ON tour (id);
CREATE INDEX tour_price
  ON tour (price);
CREATE INDEX tour_dates
  ON tour (departure_date, return_date);
CREATE INDEX tour_facility_id
  ON tour (facility_id);
CREATE INDEX tour_resort_id
  ON tour (resort_id);

CREATE UNIQUE INDEX resort_id
  ON resort (id);
CREATE INDEX resort_address_id
  ON resort (address_id);
CREATE INDEX resort_avg_opinion
  ON resort (avg_opinion);

CREATE UNIQUE INDEX app_user_id
  ON app_user (id);
CREATE UNIQUE INDEX app_user_mail
  ON app_user (mail);
CREATE INDEX app_user_password_hash
  ON app_user (password_hash);

CREATE UNIQUE INDEX opinion_id
  ON opinion (id);
CREATE INDEX opinion_send_date
  ON opinion (send_date);
CREATE INDEX opinion_resort_id
  ON opinion (contract_id);

CREATE UNIQUE INDEX address_id
  ON address (id);
CREATE INDEX address_city_id
  ON address (city_id);

CREATE UNIQUE INDEX city_id
  ON city (id);
CREATE INDEX city_country
  ON city (country);
CREATE INDEX city_name
  ON city (name);

CREATE UNIQUE INDEX facility_id
  ON facility (id);

CREATE UNIQUE INDEX room_id
  ON room (id);
CREATE INDEX room_resort_id
  ON room (resort_id);

CREATE UNIQUE INDEX room_tour_id
  ON room_tour (id);
CREATE INDEX room_tour_room_id
  ON room_tour (room_id);
CREATE INDEX room_tour_tour_id
  ON room_tour (tour_id);

CREATE INDEX room_contract_id
  ON room_contract (id);
CREATE INDEX room_contract_contact_id
  ON room_contract (contract_id);
CREATE INDEX room_room_id
  ON room_contract (room_id);

-- Foreign keys
ALTER TABLE notification ADD CONSTRAINT FKNotification87969 FOREIGN KEY (contract_id) REFERENCES contract (id) ON DELETE CASCADE;
ALTER TABLE notification ADD CONSTRAINT FKNotification6666 FOREIGN KEY (notification_type_id) REFERENCES notification_type (id) ON DELETE SET NULL;
ALTER TABLE opinion ADD CONSTRAINT FK_OPINION_CONTRACT FOREIGN KEY (contract_id) REFERENCES contract (id) ON DELETE CASCADE;
ALTER TABLE address ADD CONSTRAINT FKAddress387723 FOREIGN KEY (city_id) REFERENCES city (id);
ALTER TABLE resort ADD CONSTRAINT FKResort585514 FOREIGN KEY (address_id) REFERENCES address (id) ON DELETE CASCADE;
ALTER TABLE contract ADD CONSTRAINT FKContract29695 FOREIGN KEY (user_id) REFERENCES app_user (id) ON DELETE CASCADE;
ALTER TABLE room ADD CONSTRAINT FKRoom672956 FOREIGN KEY (resort_id) REFERENCES resort (id) ON DELETE CASCADE;
ALTER TABLE tour ADD CONSTRAINT FKTour963567 FOREIGN KEY (facility_id) REFERENCES facility (id);
ALTER TABLE room_tour ADD CONSTRAINT FKRoom_tour741677 FOREIGN KEY (room_id) REFERENCES room (id) ON DELETE CASCADE;
ALTER TABLE room_tour ADD CONSTRAINT FKRoom_tour151885 FOREIGN KEY (tour_id) REFERENCES tour (id) ON DELETE CASCADE;
ALTER TABLE contract ADD CONSTRAINT FKContract551734 FOREIGN KEY (tour_id) REFERENCES tour (id) ON DELETE CASCADE ;
ALTER TABLE room_contract ADD CONSTRAINT FKRoom_contract200774 FOREIGN KEY (room_id) REFERENCES room (id) ON DELETE CASCADE ;
ALTER TABLE room_contract ADD CONSTRAINT FKRoom_contract959389 FOREIGN KEY (contract_id) REFERENCES contract (id) ON DELETE CASCADE ;
ALTER TABLE tour ADD CONSTRAINT FKTour78363 FOREIGN KEY (resort_id) REFERENCES resort (id) ON DELETE CASCADE ;
ALTER TABLE app_user ADD CONSTRAINT FKUserRole FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE SET NULL;
ALTER TABLE role_permission ADD CONSTRAINT FKRolePermissionRole FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE ;
ALTER TABLE role_permission ADD CONSTRAINT FKROlePermissionPermission FOREIGN KEY (permission_id) REFERENCES permission (id) ON DELETE CASCADE ;

--ASSIGN PERMISSIONS
INSERT INTO role_permission(role_id, permission_id) VALUES
--ADMIN PERMISSIONS
(1, 1), (1, 2), (1, 3), (1, 4),
(1, 5), (1, 6), (1, 7), (1, 8),
(1, 9), (1, 10), (1, 11), (1, 12),
(1, 13), (1, 14), (1, 15), (1, 16),
(1, 17), (1, 18), (1, 19), (1, 20),
(1, 21), (1, 22), (1, 23), (1, 24),
(1, 25), (1, 26), (1, 27), (1, 28),
(1, 29), (1, 30), (1, 31), (1, 32),
(1, 33), (1, 34), (1, 35), (1, 36);

--USER_PERMISSIONS
INSERT INTO role_permission (role_id, permission_id, only_related) VALUES
(2, 2, true), (2, 3, true), (2, 4, true),
(2, 5, true), (2, 6, true),
(2, 7, true), (2, 8, false),
(2, 12, true), (2, 14, true),
(2, 15, true), (2, 16, true),
(2, 17, false),
(2, 19, false),
(2, 21, false),
(2, 23, false),
(2, 25, false),
(2, 27, false),
(2, 29, false),

--GUESTS PERMISSIONS
(3, 17, false),
(3, 19, false),
(3, 21, false),
(3, 23, false),
(3, 25, false),
(3, 27, false),
(3, 29, false);