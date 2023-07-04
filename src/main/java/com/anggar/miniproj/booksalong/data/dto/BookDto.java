package com.anggar.miniproj.booksalong.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.anggar.miniproj.booksalong.data.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class BookDto {
    
    @Getter
    private long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String ISBN;

    @Getter
    @Setter
    private List<AuthorDto> authors;

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
        String title,
        String ISBN,
        List<Long> authorIds
    ) { }

    public static BookDto fromEntity(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .ISBN(book.getISBN())
                .authors(AuthorDto.fromEntities(book.getAuthors()))
                .build();
    }

    public static List<BookDto> fromEntities(List<Book> books) {
        return books.stream().map(BookDto::fromEntity).collect(Collectors.toList());
    }
}
