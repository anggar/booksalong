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
public class Book extends BaseSingleEntity {

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
    @Column
    private String coverImage;

    @Getter
    @Setter
    @Column
    private String description;

    @Getter
    @Setter
    @Column
    private Long pageNumbers;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(
        name = "author_book",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    @Getter
    @Setter
    @OneToMany(mappedBy = "book")
    private List<Tracker> bookTrackers;
}
