package com.anggar.miniproj.booksalong.web.service;

import com.anggar.miniproj.booksalong.data.dto.BookDto;
import com.anggar.miniproj.booksalong.data.entity.Author;
import com.anggar.miniproj.booksalong.data.entity.Book;
import com.anggar.miniproj.booksalong.data.repository.AuthorRepository;
import com.anggar.miniproj.booksalong.data.repository.BookRepository;
import com.anggar.miniproj.booksalong.web.exception.ItemNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Value("${app.upload-directory}")
    private String UPLOAD_PATH;

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
                .orElseThrow(() -> new ItemNotFoundException(Book.class));
    }

    @Transactional(readOnly = true)
    public List<Book> findByTitle(String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }

    @Transactional(readOnly = true)
    public List<Book> searchByTitle(String bookTitle) {
        return bookRepository.searchByTitleContainingIgnoreCase(bookTitle);
    }

    @Transactional
    public Book create(BookDto.BookCreateRequest book) {
        var bookBuilder = Book.builder()
            .title(book.title())
            .ISBN(book.ISBN())
            .pageNumbers(book.pageNumbers())
            .description(book.description());

        var authors = new ArrayList<Author>();

        for (var authorId: book.authorIds()) {
            var author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new ItemNotFoundException(Author.class));

            authors.add(author);
        }

        bookBuilder.authors(authors);
        
        return bookRepository.save(bookBuilder.build());
    }

    @Transactional
    public Book update(BookDto.BookUpdateRequest bookUpdate) {
        var savedBook = bookRepository.findById(bookUpdate.id())
            .orElseThrow(() -> new ItemNotFoundException(Book.class));

        if (Optional.ofNullable(bookUpdate.title()).isPresent()) {
            savedBook.setTitle(bookUpdate.title());
        }

        if (Optional.ofNullable(bookUpdate.ISBN()).isPresent()) {
            savedBook.setISBN(bookUpdate.ISBN());
        }

        if (Optional.ofNullable(bookUpdate.pageNumbers()).isPresent()) {
            savedBook.setPageNumbers(bookUpdate.pageNumbers());
        }

        if (Optional.ofNullable(bookUpdate.description()).isPresent()) {
            savedBook.setDescription(bookUpdate.description());
        }

        return bookRepository.save(savedBook);
    }

    public void delete(long id) {
        bookRepository.findById(id)
            .orElseThrow(() -> new ItemNotFoundException(Book.class));
        bookRepository.deleteById(id);
    }

    public Book uploadCoverImage(Long id, MultipartFile file) throws IOException {
        var savedBook = bookRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(Book.class));

        var filename = id.toString() + "-" + file.getOriginalFilename();

        file.transferTo(new File(UPLOAD_PATH + filename));
        savedBook.setCoverImage(filename);

        return bookRepository.save(savedBook);
    }
}
