package com.anggar.miniproj.booksalong.data.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.anggar.miniproj.booksalong.data.entity.Author;

import com.anggar.miniproj.booksalong.data.entity.Book;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

public abstract class AuthorDto {
     public sealed interface Data permits Data.Compact, Data.Complete, Data.CompleteWithBook {
         record Compact (long id, String name) implements Data {
             public Compact(Author author) {
                 this(author.getId(), author.getName());
             }
         }
         record Complete (
                 long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt
         ) implements Data {
             public Complete(Author author) {
                 this(author.getId(), author.getName(), author.getCreatedAt(), author.getUpdatedAt());
             }
         }
         record CompleteWithBook (
                 long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt,
                 List<BookDto.Data> books
         ) implements Data {
            public CompleteWithBook(Author author) {
                this(author.getId(), author.getName(), author.getCreatedAt(), author.getUpdatedAt(),
                        BookDto.fromEntities(author.getBooks(), BookDto.Data.Compact.class));
            }
        }
     }

    @AllArgsConstructor
    public static class SingleAuthor<T extends Data> {
        @Getter
        private T author;

        public static <K extends Data> SingleAuthor<? extends Data> fromEntity(Author author, Class<K> cls) {
            return new SingleAuthor<>(AuthorDto.fromEntity(author, cls));
        }
    }

    @AllArgsConstructor
    public static class SingleAuthorWithBooks<T extends Data> {
        @Getter
        private T author;

        @Getter
        private List<BookDto.Data> books;

        public static <K extends Data> SingleAuthorWithBooks<? extends Data> fromEntity(Author author, List<Book> books, Class<K> cls) {
            return new SingleAuthorWithBooks<>(AuthorDto.fromEntity(author, cls), BookDto.fromEntities(books, BookDto.Data.Compact.class));
        }
    }

    @AllArgsConstructor
    public static class MultipleAuthors {

        @Getter
        private List<? extends Data> authors;

        public static MultipleAuthors fromEntities(List<Author> authors) {
            return new MultipleAuthors(AuthorDto.fromEntities(authors));
        }
    }

    public static Data fromEntity(Author author, Class<? extends Data> cls) {
        if (cls == Data.Complete.class) {
            return new Data.Complete(author);
        }

        return new Data.Compact(author);
    }

    public static List<Data> fromEntities(List<Author> authors) {
       return authors.stream().map(Data.Compact::new).collect(Collectors.toList());
    }

    public record AuthorCreateRequest (
            @NotEmpty String name
    ) {
        public Author toEntity() {
            return Author.builder()
                    .name(this.name)
                    .build();
        }
    }

}
