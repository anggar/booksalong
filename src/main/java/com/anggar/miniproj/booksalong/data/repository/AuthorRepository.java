package com.anggar.miniproj.booksalong.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anggar.miniproj.booksalong.data.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}