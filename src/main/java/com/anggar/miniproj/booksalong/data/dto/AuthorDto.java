package com.anggar.miniproj.booksalong.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.anggar.miniproj.booksalong.data.entity.Author;

import lombok.*;

@Builder
public class AuthorDto {

    @Getter
    private long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @AllArgsConstructor
    public static class SingleAuthor<T> {
        private T author;

        public static SingleAuthor<AuthorDto> fromEntity(Author author) {
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
        return new Author(id, name, null);
    }

    public static AuthorDto fromEntity(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }

    public  static List<AuthorDto> fromEntities(List<Author> authors) {
       return authors.stream().map(AuthorDto::fromEntity).collect(Collectors.toList());
    }
}
