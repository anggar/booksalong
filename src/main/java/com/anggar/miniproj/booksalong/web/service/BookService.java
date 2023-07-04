package com.anggar.miniproj.booksalong.web.service;

import com.anggar.miniproj.booksalong.data.entity.Book;
import com.anggar.miniproj.booksalong.data.repository.BookRepository;
import com.anggar.miniproj.booksalong.web.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Book findById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Book> findByTitle(String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }

    public Book create(Book book) {
        return bookRepository.save(book);
    }
}
