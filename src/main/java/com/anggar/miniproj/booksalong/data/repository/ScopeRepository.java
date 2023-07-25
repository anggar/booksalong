package com.anggar.miniproj.booksalong.data.repository;

import com.anggar.miniproj.booksalong.data.entity.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScopeRepository extends JpaRepository<Scope, Long> {
    Optional<Scope> findByName(String name);
}
