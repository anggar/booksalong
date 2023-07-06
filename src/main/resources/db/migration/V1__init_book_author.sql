CREATE TABLE author (
    id BIGSERIAL PRIMARY KEY,
    name character varying(255) NOT NULL,
    created_at timestamp(6), 
    updated_at timestamp(6)
);

CREATE TABLE book (
    id BIGSERIAL PRIMARY KEY,
    isbn character varying(255) NOT NULL UNIQUE,
    title character varying(255) NOT NULL,
    created_at timestamp(6), 
    updated_at timestamp(6)
);

CREATE TABLE author_book (
    author_id bigint NOT NULL REFERENCES author(id),
    book_id bigint NOT NULL REFERENCES book(id)
);
