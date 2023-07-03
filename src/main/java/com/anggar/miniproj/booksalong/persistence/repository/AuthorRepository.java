package com.anggar.miniproj.booksalong.persistence.repository;

import com.anggar.miniproj.booksalong.persistence.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}