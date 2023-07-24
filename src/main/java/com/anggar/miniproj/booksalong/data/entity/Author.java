package com.anggar.miniproj.booksalong.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private List<Book> books;
}
