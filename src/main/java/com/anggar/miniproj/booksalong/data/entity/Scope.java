package com.anggar.miniproj.booksalong.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.Collection;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Scope extends BaseSingleEntity {

    @Getter
    @Setter
    @Column
    private String name;

    @ManyToMany(mappedBy = "scopes")
    private Collection<Role> roles;

}
