package com.anggar.miniproj.booksalong.data.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.anggar.miniproj.booksalong.data.entity.Book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
public class BookDto {
    
    @Getter
    private long id;

    @Getter
    private String title;

    @Getter
    private String ISBN;

    @Getter
    private List<AuthorDto> authors;

    @Getter
    private LocalDateTime createdAt;

    @Getter
    private LocalDateTime updatedAt;

    @AllArgsConstructor
    public static class SingleBook<T> {
        @Getter
        private T book;

        public static SingleBook<BookDto> fromEntity(Book book) {
            return new SingleBook<>(BookDto.fromEntity(book));
        }
    }

    @AllArgsConstructor
    public static class MultipleBooks {
        @Getter
        private List<BookDto> books;

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


    public static BookDto fromEntity(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .ISBN(book.getISBN())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .authors(AuthorDto.fromEntities(book.getAuthors()))
                .build();
    }

    public static List<BookDto> fromEntities(List<Book> books) {
        return books.stream().map(BookDto::fromEntity).collect(Collectors.toList());
    }
}
