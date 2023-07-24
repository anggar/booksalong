package com.anggar.miniproj.booksalong.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author extends BaseSingleEntity {

    @Getter
    @Setter
    @Column(nullable = false)
    private String name;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;
}
