DROP TABLE IF EXISTS  trips;
DROP TABLE IF EXISTS  users;
DROP TABLE IF EXISTS  cars;
DROP SEQUENCE IF EXISTS global_seq;
DROP TYPE IF EXISTS car_status;
DROP TYPE IF EXISTS trip_status;

CREATE SEQUENCE global_seq START WITH 100;

/*
CREATE TYPE car_status  AS ENUM ('VACANT', 'BUSY', 'INACTIVE');
CREATE TYPE trip_status AS ENUM ('WAITING', 'TRAVELLING', 'FINISHED');

сереализация таких типов работает криво (но в целом ее можно заставить рабоать),
поэтому в базе будем хранить как VARCHAR
во всяком случае пока
*/

CREATE TABLE users
(
    id        INTEGER    PRIMARY KEY DEFAULT nextval('global_seq'),
    name      VARCHAR    NOT NULL,
    email     VARCHAR    NOT NULL,
    password  VARCHAR    NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users(email);


CREATE TABLE cars
(
    id         INTEGER    PRIMARY KEY DEFAULT nextval('global_seq'),
    name       VARCHAR    NOT NULL,
    status     VARCHAR    NOT NULL,
    latitude   FLOAT      NOT NULL,
    longitude  FLOAT      NOT NULL
);

CREATE TABLE trips
(
    id           INTEGER    PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id      INTEGER    NOT NULL,
    car_id       INTEGER    NOT NULL,
    status       VARCHAR    NOT NULL,
    start_lat    FLOAT      NOT NULL,
    start_long   FLOAT      NOT NULL,
    finish_lat   FLOAT      NOT NULL,
    finish_long  FLOAT      NOT NULL,

    FOREIGN KEY (car_id)  REFERENCES cars  (id) ON DELETE RESTRICT,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT
);
