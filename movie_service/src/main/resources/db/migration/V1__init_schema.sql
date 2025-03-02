CREATE TABLE genres (
                        id bigserial PRIMARY KEY,
                        name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE movies (
                        id bigserial PRIMARY KEY,
                        avg_rating numeric(3, 2),
                        created_at timestamp(6) NOT NULL,
                        description text,
                        duration integer NOT NULL,
                        language varchar(50) NOT NULL,
                        poster text,
                        release_year integer NOT NULL,
                        title varchar(255) NOT NULL
);

CREATE TABLE movie_genre (
                             movie_id bigint NOT NULL REFERENCES movies(id),
                             genre_id bigint NOT NULL REFERENCES genres(id)
);

CREATE TABLE people (
                        id bigserial PRIMARY KEY,
                        full_name varchar(255) NOT NULL,
                        role varchar(50) NOT NULL
);

CREATE TABLE movie_person (
                              person_id bigint NOT NULL REFERENCES people(id),
                              movie_id bigint NOT NULL REFERENCES movies(id)
);

CREATE TABLE users (
                       id bigserial PRIMARY KEY,
                       created_at timestamp(6) NOT NULL,
                       email varchar(100) NOT NULL UNIQUE,
                       is_blocked boolean NOT NULL,
                       password varchar(255) NOT NULL,
                       role varchar(255) NOT NULL,
                       username varchar(50) NOT NULL UNIQUE
);

CREATE TABLE ratings (
                         id bigserial PRIMARY KEY,
                         created_at timestamp(6) NOT NULL,
                         rating integer NOT NULL,
                         updated_at timestamp(6),
                         movie_id bigint NOT NULL REFERENCES movies(id),
                         user_id bigint NOT NULL REFERENCES users(id)
);

CREATE TABLE reviews (
                         id bigserial PRIMARY KEY,
                         content text NOT NULL,
                         created_at timestamp(6) NOT NULL,
                         rating integer NOT NULL,
                         status varchar(255) NOT NULL,
                         updated_at timestamp(6),
                         movie_id bigint NOT NULL REFERENCES movies(id),
                         user_id bigint NOT NULL REFERENCES users(id)
);

CREATE TABLE watchlists (
                            id bigserial PRIMARY KEY,
                            added_at timestamp(6) NOT NULL,
                            movie_id bigint NOT NULL REFERENCES movies(id),
                            user_id bigint NOT NULL REFERENCES users(id)
);
