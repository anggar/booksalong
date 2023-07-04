package com.anggar.miniproj.booksalong.web.controller;

import com.anggar.miniproj.booksalong.data.dto.BookDto;
import com.anggar.miniproj.booksalong.data.repository.AuthorRepository;
import com.anggar.miniproj.booksalong.data.repository.BookRepository;
import com.anggar.miniproj.booksalong.web.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public BookDto.MultipleBooks findAll() {
        var books = bookService.findAll();
        return BookDto.MultipleBooks.fromEntities(books);
    }

    @GetMapping("/title")
    public BookDto.MultipleBooks findByTitle(@RequestBody BookDto.BookTitle bookTitle) {
        var books = bookService.findByTitle(bookTitle.title());
        return BookDto.MultipleBooks.fromEntities(books);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public BookDto.SingleBook<BookDto> findOne(@PathVariable long id) {
        var book = bookService.findById(id);
        return BookDto.SingleBook.fromEntity(book);
    }
}
