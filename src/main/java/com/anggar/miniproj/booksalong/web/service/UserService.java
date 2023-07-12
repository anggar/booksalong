package com.anggar.miniproj.booksalong.web.service;

import com.anggar.miniproj.booksalong.data.dto.UserDto;
import com.anggar.miniproj.booksalong.data.entity.User;
import com.anggar.miniproj.booksalong.data.repository.UserRepository;
import com.anggar.miniproj.booksalong.web.exception.DuplicateDataException;
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

    @Transactional
    public User register(UserDto.UserRegistrationRequest body) {
        userRepository.findByEmailOrUsername(body.email(), body.username())
                .stream().findAny().ifPresent(entity -> {
                    throw new DuplicateDataException("Duplicated data found");
        });

        var user = body.toEntity(this.passwordEncoder);
        return userRepository.save(user);
    }

    public User login(UserDto.UserLoginRequest body) {
        var savedUser = userRepository.findByEmail(body.email());

        if (savedUser.isPresent()) {
            var user = savedUser.get();
            if (passwordEncoder.matches(body.password(), user.getPassword())) {
                return savedUser.get();
            } else {
//                TODO: create general AppException
                throw new RuntimeException("Wrong password or email");
            }
        } else {
            throw new UsernameNotFoundException("Wrong password or email");
        }
    }
}
