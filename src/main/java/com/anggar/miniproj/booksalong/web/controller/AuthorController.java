package com.anggar.miniproj.booksalong.web.controller;

import com.anggar.miniproj.booksalong.persistence.model.Author;
import com.anggar.miniproj.booksalong.persistence.repository.AuthorRepository;
import com.anggar.miniproj.booksalong.web.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public Iterable<Author> findAll() {
        return authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Author findOne(@PathVariable long id) {
        return authorRepository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author create(@RequestBody Author author) {
        return authorRepository.save(author);
    }
}
