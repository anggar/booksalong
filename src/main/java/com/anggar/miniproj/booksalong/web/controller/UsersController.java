package com.anggar.miniproj.booksalong.web.controller;

import com.anggar.miniproj.booksalong.data.dto.UserDto;
import com.anggar.miniproj.booksalong.security.JwtUtils;
import com.anggar.miniproj.booksalong.web.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto.SingleUser<? extends UserDto.Data> register(@RequestBody @Valid UserDto.UserRegistrationRequest body) {
        var user = userService.register(body);

        return UserDto.SingleUser.fromEntity(user, UserDto.Data.CompleteNoPassword.class);
    }

    @PostMapping("/login")
    public UserDto.SingleUserWithToken login(@RequestBody @Valid UserDto.UserLoginRequest body) {
        var user = userService.login(body);

        return new UserDto.SingleUserWithToken(user, jwtUtils.encode(user.getEmail()));
    }
}
