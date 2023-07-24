package com.anggar.miniproj.booksalong.web.controller;

import com.anggar.miniproj.booksalong.data.dto.BookDto;
import com.anggar.miniproj.booksalong.web.exception.IdMismatchException;
import com.anggar.miniproj.booksalong.web.service.BookService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public BookDto.MultipleBooks findAll() {
        var books = bookService.findAll();
        return BookDto.MultipleBooks.fromEntities(books);
    }

    @GetMapping("/title")
    public BookDto.MultipleBooks findByTitle(@RequestBody BookDto.BookTitle bookTitle) {
        var books = bookService.findByTitle(bookTitle.title());
        return BookDto.MultipleBooks.fromEntities(books);
    }

    @GetMapping("/{id}/")
    public BookDto.SingleBook<? extends BookDto.Data> findOne(@PathVariable long id) {
        var book = bookService.findById(id);
        return BookDto.SingleBook.fromEntity(book);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto.SingleBook<? extends BookDto.Data> create(@RequestBody @Valid BookDto.BookCreateRequest body) {
        var book = bookService.create(body);
        return BookDto.SingleBook.fromEntity(book);
    }
    
    @PutMapping("/{id}/")
    public BookDto.SingleBook<? extends BookDto.Data> update(
            @RequestBody @Valid BookDto.BookUpdateRequest body,
            @PathVariable("id") long id
    ) {
        if (id != body.id()) {
            throw new IdMismatchException();
        }

        var book = bookService.update(body);
        return BookDto.SingleBook.fromEntity(book);
    }

    @DeleteMapping("/{id}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        bookService.delete(id);
    }

    @PutMapping("/{id}/cover")
    public BookDto.SingleBook<? extends BookDto.Data> updateCover(
            @PathVariable("id") long id,
            @RequestParam MultipartFile file
    ) throws IOException {
        var book = bookService.uploadCoverImage(id, file);

        return BookDto.SingleBook.fromEntity(book);
    }

}
