-- Seed data for Movie Booking System

INSERT INTO users (name, email, phone) VALUES
('Rahul Sharma', 'rahul@example.com', '9876543210'),
('Priya Patel', 'priya@example.com', '9876543211'),
('Amit Kumar', 'amit@example.com', '9876543212');

INSERT INTO movies (title, description, genre, duration_mins, rating, language, poster_url, release_date) VALUES
('Inception', 'A thief who steals corporate secrets through dream-sharing technology is given the inverse task of planting an idea.', 'Sci-Fi', 148, 8.8, 'English', 'https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg', '2010-07-16'),
('Interstellar', 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity''s survival.', 'Sci-Fi', 169, 8.6, 'English', 'https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg', '2014-11-07'),
('The Dark Knight', 'Batman faces the Joker, a criminal mastermind who plunges Gotham into anarchy.', 'Action', 152, 9.0, 'English', 'https://image.tmdb.org/t/p/w500/qJ2tW6WMU2obteXgFqOllVnwWAD.jpg', '2008-07-18'),
('3 Idiots', 'Two friends search for their long-lost companion while recounting college memories.', 'Comedy', 170, 8.4, 'Hindi', 'https://image.tmdb.org/t/p/w500/66A9siZxD7R6sP8p3q1k2l3m4n5.jpg', '2009-12-25'),
('Dune: Part Two', 'Paul Atreides unites with Chani and the Fremen while seeking revenge against the conspirators.', 'Sci-Fi', 166, 8.5, 'English', 'https://image.tmdb.org/t/p/w500/1pdfLvkbY9ohJlCjQH2CZjjYVvJ.jpg', '2024-03-01');

INSERT INTO theaters (name, location, city) VALUES
('PVR Nexus', 'Koramangala Forum Mall', 'Bangalore'),
('INOX Brookefield', 'ITPL Main Road', 'Bangalore'),
('Cinepolis', 'Phoenix Marketcity', 'Mumbai');

INSERT INTO screens (theater_id, name, total_seats) VALUES
(1, 'Screen 1', 40),
(1, 'Screen 2', 40),
(2, 'Audi 1', 40),
(3, 'Hall A', 40);

-- Seats for each screen: rows A-D, seats 1-10 (40 seats)
INSERT INTO seats (screen_id, row_label, seat_number, seat_type)
SELECT s.id,
       chr(64 + r.n),
       c.n,
       CASE WHEN r.n <= 2 THEN 'REGULAR' ELSE 'PREMIUM' END
FROM screens s
CROSS JOIN generate_series(1, 4) AS r(n)
CROSS JOIN generate_series(1, 10) AS c(n);

-- Shows over the next few days
INSERT INTO shows (movie_id, screen_id, show_time, ticket_price) VALUES
(1, 1, CURRENT_DATE + INTERVAL '1 day' + TIME '10:00', 250.00),
(1, 1, CURRENT_DATE + INTERVAL '1 day' + TIME '14:30', 300.00),
(1, 1, CURRENT_DATE + INTERVAL '1 day' + TIME '19:00', 350.00),
(2, 2, CURRENT_DATE + INTERVAL '1 day' + TIME '11:00', 280.00),
(2, 2, CURRENT_DATE + INTERVAL '1 day' + TIME '16:00', 320.00),
(3, 3, CURRENT_DATE + INTERVAL '1 day' + TIME '13:00', 270.00),
(3, 3, CURRENT_DATE + INTERVAL '1 day' + TIME '18:30', 340.00),
(4, 4, CURRENT_DATE + INTERVAL '1 day' + TIME '12:00', 200.00),
(4, 4, CURRENT_DATE + INTERVAL '1 day' + TIME '17:00', 220.00),
(5, 1, CURRENT_DATE + INTERVAL '2 day' + TIME '11:30', 300.00),
(5, 2, CURRENT_DATE + INTERVAL '2 day' + TIME '15:00', 320.00),
(1, 3, CURRENT_DATE + INTERVAL '2 day' + TIME '20:00', 360.00),
(2, 4, CURRENT_DATE + INTERVAL '2 day' + TIME '19:30', 310.00);

-- Sample booking so some seats appear taken
INSERT INTO bookings (user_id, show_id, total_amount, status, booking_ref) VALUES
(1, 1, 500.00, 'CONFIRMED', 'BK-SAMPLE-001');

INSERT INTO booking_seats (booking_id, seat_id)
SELECT 1, id FROM seats WHERE screen_id = 1 AND row_label = 'A' AND seat_number IN (1, 2);
