package com.anggar.miniproj.booksalong.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anggar.miniproj.booksalong.data.entity.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitle(String title);

    List<Book> searchByTitleContainingIgnoreCase(String title);
}
