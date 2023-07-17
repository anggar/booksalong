package com.anggar.miniproj.booksalong.data.repository;

import com.anggar.miniproj.booksalong.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email OR u.username = :username")
    List<User> findByEmailOrUsername(@Param("email") String email, @Param("username") String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}