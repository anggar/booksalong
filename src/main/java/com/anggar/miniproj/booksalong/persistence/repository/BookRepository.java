package com.anggar.miniproj.booksalong.persistence.repository;

import com.anggar.miniproj.booksalong.persistence.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitle(String title);
}
