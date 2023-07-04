package com.anggar.miniproj.booksalong.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anggar.miniproj.booksalong.data.entity.Author;
import com.anggar.miniproj.booksalong.data.repository.AuthorRepository;
import com.anggar.miniproj.booksalong.web.exception.ItemNotFoundException;

@Service
public class AuthorService {
   
    @Autowired
    private AuthorRepository authorRepository;

    public Author create(Author author) {
        return authorRepository.save(author);
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author findById(long id) {
        return authorRepository.findById(id)
            .orElseThrow(ItemNotFoundException::new);
    }
}
