package com.anggar.miniproj.booksalong.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String ISBN;

    @Getter
    @Setter
    @Column(nullable = false)
    private String title;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(
        name = "author_book",
        joinColumns = @JoinColumn(name = "author_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Author> authors;
}
