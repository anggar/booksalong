package com.anggar.miniproj.booksalong.data.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.anggar.miniproj.booksalong.data.entity.Book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

public abstract class BookDto {
    public sealed interface Data permits Data.Complete {
        record Complete (
                long id,
                String title,
                String ISBN,
                String coverImage,
                List<AuthorDto.Data> authors,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) implements Data {
            public Complete(Book book) {
                this(book.getId(), book.getTitle(), book.getISBN(),
                        book.getCoverImage(),
                        AuthorDto.fromEntities(book.getAuthors()),
                        book.getCreatedAt(), book.getUpdatedAt());
            }
        }
    }

    @AllArgsConstructor
    public static class SingleBook<T extends Data> {
        @Getter
        private T book;

        public static SingleBook<? extends Data> fromEntity(Book book) {
            return new SingleBook<>(BookDto.fromEntity(book));
        }
    }

    @AllArgsConstructor
    public static class MultipleBooks {
        @Getter
        private List<? extends Data> books;

        public static MultipleBooks fromEntities(List<Book> books) {
            return new MultipleBooks(BookDto.fromEntities(books));
        }
    }

    public record BookTitle (
        String title
    ) {}

    public record BookCreateRequest (
        @NotEmpty String title,
        @NotEmpty String ISBN,
        @NotEmpty List<Long> authorIds
    ) { }

    public record BookUpdateRequest (
        @Min(1) long id,
        String title,
        String ISBN
    ) { }


    public static Data fromEntity(Book book) {
        return new Data.Complete(book);
    }

    public static List<Data> fromEntities(List<Book> books) {
        return books.stream().map(Data.Complete::new).collect(Collectors.toList());
    }
}
