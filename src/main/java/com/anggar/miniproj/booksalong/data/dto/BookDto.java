package com.anggar.miniproj.booksalong.data.dto;

import java.util.List;

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

    @AllArgsConstructor
    private static class SingleBook<T> {
        private T Book;
    }

    @AllArgsConstructor
    private static class MultipleBooks<T> {
        private List<BookDto> Books;
    }
}
