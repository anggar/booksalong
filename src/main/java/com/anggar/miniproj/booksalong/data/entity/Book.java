package com.anggar.miniproj.booksalong.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "books")
@NoArgsConstructor
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
    @ManyToMany(mappedBy = "books")
    private Set<Author> authors;

    @Override
    public String toString() {
        return "Book [id=" + id + ", isbn=" + ISBN + ", title=" + title + ", author=" + authors + "]";
    }
}
