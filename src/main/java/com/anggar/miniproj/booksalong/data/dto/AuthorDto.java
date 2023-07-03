package com.anggar.miniproj.booksalong.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.anggar.miniproj.booksalong.data.entity.Author;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
            var authorList = authors.stream().map(entity -> {
                return AuthorDto.fromEntity(entity);
            }).collect(Collectors.toList());

            return new MultipleAuthors(authorList);
        }
    }

    public Author toEntity() {
        return new Author(id, name, null);
    }

    private static AuthorDto fromEntity(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }
}
