package com.anggar.miniproj.booksalong.web.service;

import com.anggar.miniproj.booksalong.data.dto.BookDto;
import com.anggar.miniproj.booksalong.data.entity.Author;
import com.anggar.miniproj.booksalong.data.entity.Book;
import com.anggar.miniproj.booksalong.data.repository.AuthorRepository;
import com.anggar.miniproj.booksalong.data.repository.BookRepository;
import com.anggar.miniproj.booksalong.web.exception.ItemNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

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

    @Transactional
    public Book create(BookDto.BookCreateRequest book) {
        var bookBuilder = Book.builder()
            .title(book.title())
            .ISBN(book.ISBN());

        var authors = new ArrayList<Author>();

        for (var authorId: book.authorIds()) {
            var author = authorRepository.findById(authorId);
            if (author.isEmpty()) {
                throw new ItemNotFoundException("Author not found");
            }
            
            authors.add(author.get());
        }

        bookBuilder.authors(authors);
        
        return bookRepository.save(bookBuilder.build());
    }

    @Transactional
    public Book update(BookDto.BookUpdateRequest bookUpdate) {
        Book savedBook = bookRepository.findById(bookUpdate.id())
            .orElseThrow(ItemNotFoundException::new);

        savedBook.setTitle(bookUpdate.title().isEmpty() ? savedBook.getTitle() : bookUpdate.title());
        savedBook.setISBN(bookUpdate.ISBN().isEmpty() ? savedBook.getISBN() : bookUpdate.ISBN());

        return bookRepository.save(savedBook);
    }

    public void delete(long id) {
        bookRepository.findById(id)
            .orElseThrow(ItemNotFoundException::new);
        bookRepository.deleteById(id);
    }
}
