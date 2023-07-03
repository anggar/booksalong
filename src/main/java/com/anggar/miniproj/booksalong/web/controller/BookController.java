package com.anggar.miniproj.booksalong.web.controller;

import com.anggar.miniproj.booksalong.persistence.model.Book;
import com.anggar.miniproj.booksalong.persistence.repository.BookRepository;
import com.anggar.miniproj.booksalong.web.exception.IdMismatchException;
import com.anggar.miniproj.booksalong.web.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/title/{bookTitle}")
    public List<Book> findByTitle(@PathVariable String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public Book findOne(@PathVariable long id) {
        return bookRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        bookRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable long id) {
        if (book.getId() != id) {
            throw new IdMismatchException("The book ID mismatched between request body.");
        }

        bookRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
        return bookRepository.save(book);
    }
}
