-- Movie Booking System Schema (PostgreSQL)

DROP TABLE IF EXISTS booking_seats CASCADE;
DROP TABLE IF EXISTS bookings CASCADE;
DROP TABLE IF EXISTS seats CASCADE;
DROP TABLE IF EXISTS shows CASCADE;
DROP TABLE IF EXISTS screens CASCADE;
DROP TABLE IF EXISTS theaters CASCADE;
DROP TABLE IF EXISTS movies CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    email           VARCHAR(150) NOT NULL UNIQUE,
    phone           VARCHAR(20)  NOT NULL,
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE movies (
    id              BIGSERIAL PRIMARY KEY,
    title           VARCHAR(200) NOT NULL,
    description     TEXT,
    genre           VARCHAR(100) NOT NULL,
    duration_mins   INTEGER      NOT NULL,
    rating          DECIMAL(3,1) NOT NULL DEFAULT 0.0,
    language        VARCHAR(50)  NOT NULL,
    poster_url      VARCHAR(500),
    release_date    DATE         NOT NULL
);

CREATE TABLE theaters (
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(150) NOT NULL,
    location        VARCHAR(255) NOT NULL,
    city            VARCHAR(100) NOT NULL
);

CREATE TABLE screens (
    id              BIGSERIAL PRIMARY KEY,
    theater_id      BIGINT       NOT NULL REFERENCES theaters(id) ON DELETE CASCADE,
    name            VARCHAR(50)  NOT NULL,
    total_seats     INTEGER      NOT NULL
);

CREATE TABLE shows (
    id              BIGSERIAL PRIMARY KEY,
    movie_id        BIGINT       NOT NULL REFERENCES movies(id) ON DELETE CASCADE,
    screen_id       BIGINT       NOT NULL REFERENCES screens(id) ON DELETE CASCADE,
    show_time       TIMESTAMP    NOT NULL,
    ticket_price    DECIMAL(10,2) NOT NULL
);

CREATE TABLE seats (
    id              BIGSERIAL PRIMARY KEY,
    screen_id       BIGINT       NOT NULL REFERENCES screens(id) ON DELETE CASCADE,
    row_label       VARCHAR(5)   NOT NULL,
    seat_number     INTEGER      NOT NULL,
    seat_type       VARCHAR(20)  NOT NULL DEFAULT 'REGULAR',
    UNIQUE (screen_id, row_label, seat_number)
);

CREATE TABLE bookings (
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT       NOT NULL REFERENCES users(id),
    show_id         BIGINT       NOT NULL REFERENCES shows(id),
    booking_time    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_amount    DECIMAL(10,2) NOT NULL,
    status          VARCHAR(20)  NOT NULL DEFAULT 'CONFIRMED',
    booking_ref     VARCHAR(30)  NOT NULL UNIQUE
);

CREATE TABLE booking_seats (
    id              BIGSERIAL PRIMARY KEY,
    booking_id      BIGINT       NOT NULL REFERENCES bookings(id) ON DELETE CASCADE,
    seat_id         BIGINT       NOT NULL REFERENCES seats(id),
    UNIQUE (booking_id, seat_id)
);

-- A seat can only be booked once per show (enforced in app + partial index via booking_seats + show)
CREATE INDEX idx_shows_movie ON shows(movie_id);
CREATE INDEX idx_shows_screen ON shows(screen_id);
CREATE INDEX idx_shows_time ON shows(show_time);
CREATE INDEX idx_seats_screen ON seats(screen_id);
CREATE INDEX idx_bookings_user ON bookings(user_id);
CREATE INDEX idx_bookings_show ON bookings(show_id);
CREATE INDEX idx_booking_seats_seat ON booking_seats(seat_id);
