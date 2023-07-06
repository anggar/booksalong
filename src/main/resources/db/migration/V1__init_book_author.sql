CREATE TABLE author (
    id BIGSERIAL PRIMARY KEY,
    name character varying(255)
);

CREATE TABLE book (
    id BIGSERIAL PRIMARY KEY,
    isbn character varying(255) NOT NULL UNIQUE,
    title character varying(255) NOT NULL
);

CREATE TABLE author_book (
    author_id bigint NOT NULL REFERENCES book(id),
    book_id bigint NOT NULL REFERENCES author(id)
);

alter table if exists author_book
    add constraint author_book_book_id_fk
    foreign key (book_id) references book;

alter table if exists author_book
    add constraint author_book_author_id_fk
    foreign key (author_id) references author;
