INSERT INTO users (email, login, name, birthday)
VALUES ('ivanov@yandex.ru', 'Ivan', 'Иван Иванов', '1988-05-11'),
       ('petrov@yandex.ru', 'Peter', 'Петр Петров', '1990-07-15'),
       ('sevenov@yandex.ru', 'Semen', 'Семен Семенов', '1992-03-20'),
       ('egorov4@yandex.ru', 'Egor', 'Егор Егоров', '1977-07-17');

INSERT INTO friendship (user_id, friend_id, status)
VALUES (1, 2, 'CONFIRMED'), (1, 3, 'UNCONFIRMED'), (3, 2, 'CONFIRMED');

INSERT INTO films (film_name, description, release_date, duration, mpa_id)
VALUES  ('Film 1', 'Description 1', '1999-09-19', 99, 1),
        ('Film 2', 'Description 2', '2022-02-22', 222, 2),
        ('Film 3', 'Description 3', '2013-03-13', 133, 3),
        ('Film 4', 'Description 4', '2004-04-04', 144, 4),
        ('Film 5', 'Description 5', '2005-05-05', 155, 5);

INSERT INTO films_genres (film_id, genre_id)
VALUES (1, 2), (1, 3), (2, 4), (3, 6), (4, 1), (4, 3), (5, 5);

INSERT INTO likes (film_id, user_id)
VALUES (1, 2), (1, 3), (1, 1);

MERGE INTO rating_mpa (mpa_id, mpa_name) VALUES     ( 1, 'G'),
                                                    ( 2, 'PG'),
                                                    ( 3, 'PG-13'),
                                                    ( 4, 'R'),
                                                    ( 5, 'NC-17');

MERGE INTO genres (genre_id, genre_name) VALUES  ( 1, 'Комедия'),
                                        (2, 'Драма'),
                                        (3, 'Мультфильм'),
                                        (4, 'Триллер'),
                                        (5, 'Документальный'),
                                        (6, 'Боевик');