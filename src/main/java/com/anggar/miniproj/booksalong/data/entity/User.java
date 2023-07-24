package com.anggar.miniproj.booksalong.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", schema = "public")
public class User extends BaseSingleEntity {

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String username;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Getter
    @Setter
    @Column(nullable = false)
    private String password;

    @Getter
    @Setter
    @Column
    private String bio;

    @Getter
    @Setter
    @OneToMany(mappedBy = "user")
    private List<Tracker> bookTrackers;
}
