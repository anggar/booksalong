package com.anggar.miniproj.booksalong.web.controller;

import com.anggar.miniproj.booksalong.data.dto.AuthorDto;
import com.anggar.miniproj.booksalong.web.service.AuthorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/")
    public AuthorDto.MultipleAuthors findAll() {
        var authors = authorService.findAll();

        return AuthorDto.MultipleAuthors.fromEntities(authors);
    }

    @GetMapping("/{id}/")
    public AuthorDto.SingleAuthorWithBooks<? extends AuthorDto.Data> findOne(@PathVariable long id) {
        var author = authorService.findById(id);

        return AuthorDto.SingleAuthorWithBooks.fromEntity(author, author.getBooks(),AuthorDto.Data.Complete.class);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto.SingleAuthor<? extends AuthorDto.Data> create(@RequestBody AuthorDto.AuthorCreateRequest author) {
        var savedAuthor = authorService.create(author.toEntity());

        return AuthorDto.SingleAuthor.fromEntity(savedAuthor, AuthorDto.Data.Complete.class);
    }
}
