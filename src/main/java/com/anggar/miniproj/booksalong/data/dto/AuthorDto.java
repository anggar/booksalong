package com.anggar.miniproj.booksalong.data.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.anggar.miniproj.booksalong.data.entity.Author;

import lombok.*;

@Builder
public class AuthorDto {

    @Getter
    private long id;

    @Getter
    private String name;

    public record AuthorDataComplete (
        long id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        public AuthorDataComplete(Author author) {
            this(author.getId(), author.getName(), author.getCreatedAt(), author.getUpdatedAt());
        }
     }


    @Getter
    @AllArgsConstructor
    public static class SingleAuthor<T> {
        private T author;

        public static SingleAuthor fromEntity(Author author, Class cls) {
            if (cls == AuthorDataComplete.class) {
                var data = new AuthorDataComplete(author);
                return new SingleAuthor<>(data);
            }

            return new SingleAuthor<>(AuthorDto.fromEntity(author));
        }
    }

    @Getter
    @AllArgsConstructor
    public static class MultipleAuthors {
        private List<AuthorDto> authors;

        public static MultipleAuthors fromEntities(List<Author> authors) {
            return new MultipleAuthors(AuthorDto.fromEntities(authors));
        }
    }

    public Author toEntity() {
        var author = new Author(name, null);
        author.setId(this.id);
        return author;
    }

    public static AuthorDto fromEntity(Author author) {
        return AuthorDto.builder()
            .id(author.getId())
            .name(author.getName())
            .build();
    }

    public static List<AuthorDto> fromEntities(List<Author> authors) {
       return authors.stream().map(AuthorDto::fromEntity).collect(Collectors.toList());
    }
}
