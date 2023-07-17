package com.anggar.miniproj.booksalong.web.service;

import com.anggar.miniproj.booksalong.data.dto.UserDto;
import com.anggar.miniproj.booksalong.data.entity.User;
import com.anggar.miniproj.booksalong.data.repository.UserRepository;
import com.anggar.miniproj.booksalong.security.AuthUserDetails;
import com.anggar.miniproj.booksalong.web.exception.DuplicateDataException;
import com.anggar.miniproj.booksalong.web.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User currentUser(AuthUserDetails authUserDetails) {
        return userRepository.findById(authUserDetails.id())
                .orElseThrow(() -> new ItemNotFoundException(User.class));
    }

    @Transactional
    public User register(UserDto.UserRegistrationRequest body) {
        userRepository.findByEmailOrUsername(body.email(), body.username())
                .stream().findAny().ifPresent(entity -> {
                    throw new DuplicateDataException(User.class);
        });

        var user = body.toEntity(this.passwordEncoder);
        return userRepository.save(user);
    }

    @Transactional
    public User login(UserDto.UserLoginRequest body) {
        return userRepository.findByEmail(body.email())
                .filter(user -> passwordEncoder.matches(body.password(), user.getPassword()))
                .orElseThrow(() -> new UsernameNotFoundException("Wrong password or email."));
    }

    @Transactional
    public User update(UserDto.UserUpdateRequest userUpdate) {
        var savedUser = userRepository.findById(userUpdate.id())
                .orElseThrow(() -> new ItemNotFoundException(User.class));

        if (userUpdate.email() != null && !userUpdate.email().equals(savedUser.getEmail())) {
            userRepository.findByEmail(userUpdate.email())
                    .ifPresent(found -> {
                        throw new DuplicateDataException(User.class, "email");
                    });
            savedUser.setEmail(userUpdate.email());
        }

        if (userUpdate.username() != null && !userUpdate.username().equals(savedUser.getUsername())) {
            userRepository.findByUsername(userUpdate.username())
                    .ifPresent(found -> {
                        throw new DuplicateDataException(User.class, "username");
                    });
            savedUser.setUsername(userUpdate.username());
        }

        if (userUpdate.password() != null) {
            savedUser.setPassword(passwordEncoder.encode(userUpdate.password()));
        }

        if (userUpdate.bio() != null) {
            savedUser.setBio(userUpdate.bio());
        }

        return userRepository.save(savedUser);
    }
}
