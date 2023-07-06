package com.anggar.miniproj.booksalong.web.controller;

import com.anggar.miniproj.booksalong.data.dto.AuthorDto;
import com.anggar.miniproj.booksalong.web.service.AuthorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public AuthorDto.MultipleAuthors findAll() {
        var authors = authorService.findAll();

        return AuthorDto.MultipleAuthors.fromEntities(authors);
    }

    @GetMapping("/{id}")
    public AuthorDto.SingleAuthor<AuthorDto> findOne(@PathVariable long id) {
        var author = authorService.findById(id);

        return AuthorDto.SingleAuthor.fromEntity(author, AuthorDto.AuthorDataComplete.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto.SingleAuthor<AuthorDto> create(@RequestBody AuthorDto author) {
        var savedAuthor = authorService.create(author.toEntity());

        return AuthorDto.SingleAuthor.fromEntity(savedAuthor, AuthorDto.AuthorDataComplete.class);
    }
}
