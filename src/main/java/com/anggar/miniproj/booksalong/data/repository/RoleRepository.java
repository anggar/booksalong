package com.anggar.miniproj.booksalong.data.repository;

import com.anggar.miniproj.booksalong.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
