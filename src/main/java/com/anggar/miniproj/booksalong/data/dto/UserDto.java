package com.anggar.miniproj.booksalong.data.dto;

import com.anggar.miniproj.booksalong.data.entity.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public abstract class UserDto {
    public sealed interface Data permits Data.CompleteNoPassword {
        record CompleteNoPassword(
                long id,
                String username,
                String email,
                String bio,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) implements Data {
            public CompleteNoPassword(User user) {
                this(user.getId(), user.getUsername(), user.getEmail(), user.getBio(),
                        user.getCreatedAt(), user.getUpdatedAt());
            }
        }
    }

    public record UserRegistrationRequest(
            @NotEmpty String username,
            @NotEmpty String email,
            @NotEmpty String password,
            @NotEmpty String role,
            String bio
    ) {
        public User toEntity(PasswordEncoder passwordEncoder) {
            return User.builder()
                    .username(this.username)
                    .email(this.email)
                    .password(passwordEncoder.encode(this.password))
                    .bio(this.bio)
                    .build();
        }
    }

    public record UserLoginRequest (
            @NotEmpty String email,
            @NotEmpty String password
    ) {
    }

    public record UserUpdateRequest(
            @NotNull Long id,
            String username,
            String email,
            String password,
            String bio
    ) {}

    public record SingleUserWithToken (
        Data.CompleteNoPassword user,
        String token
    ) {
        public SingleUserWithToken(User userEntity, String token) {
            this((Data.CompleteNoPassword) UserDto.fromEntity(userEntity, Data.CompleteNoPassword.class), token);
        }
    }

    @AllArgsConstructor
    public static class SingleUser<T> {
        @Getter
        private T user;

        public static UserDto.SingleUser<? extends UserDto.Data> fromEntity(User user, Class<? extends UserDto.Data> cls) {
            return new UserDto.SingleUser<>(UserDto.fromEntity(user, cls));
        }
    }

    public static UserDto.Data fromEntity(User author, Class<? extends UserDto.Data> cls) {
        if (cls == UserDto.Data.CompleteNoPassword.class) {
            return new UserDto.Data.CompleteNoPassword(author);
        }

        return new Data.CompleteNoPassword(author);
    }
}