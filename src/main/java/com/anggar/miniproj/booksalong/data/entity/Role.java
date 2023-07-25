package com.anggar.miniproj.booksalong.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseSingleEntity {

    @Getter
    @Setter
    @Column
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(
        name = "role_scope",
        joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "scope_id", referencedColumnName = "id")
    )
    private Collection<Scope> scopes;
}