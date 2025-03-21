INSERT INTO movies (title, avg_rating, created_at, description, duration, language, poster, release_year)
VALUES
    ('Начало', 8.8, NOW(), 'Фильм Кристофера Нолана про сны и реальность.', 148, 'Русский', NULL, 2010),
    ('Титаник', 9.1, NOW(), 'Историческая драма про крушение лайнера.', 195, 'Английский', NULL, 1997),
    ('Аватар', 8.5, NOW(), 'Фантастическая сага о Пандоре.', 162, 'Английский', NULL, 2009),
    ('Бойцовский клуб', 8.8, NOW(), 'Культовый фильм с Брэдом Питтом.', 139, 'Английский', NULL, 1999),
    ('Интерстеллар', 9.0, NOW(), 'Научная фантастика о путешествиях в космосе.', 169, 'Русский', NULL, 2014);

INSERT INTO movie_genre (movie_id, genre_id)
VALUES
    (1, 4),
    (1, 7),
    (2, 2),
    (2, 9),
    (3, 4),
    (3, 5),
    (4, 2),
    (4, 13),
    (5, 4),
    (5, 10);

INSERT INTO movie_person (movie_id, person_id)
VALUES
    (1, 13),
    (1, 1),
    (2, 15),
    (2, 1),
    (3, 15),
    (4, 14),
    (4, 3),
    (5, 13),
    (5, 1);