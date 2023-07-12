package com.anggar.miniproj.booksalong.web.controller;

import com.anggar.miniproj.booksalong.data.dto.UserDto;
import com.anggar.miniproj.booksalong.security.AuthUserDetails;
import com.anggar.miniproj.booksalong.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public UserDto.SingleUser<? extends UserDto.Data> currentUser(@AuthenticationPrincipal AuthUserDetails authUserDetails) {
        var user = userService.currentUser(authUserDetails);

        return UserDto.SingleUser.fromEntity(user, UserDto.Data.CompleteNoPassword.class);
    }

    @PutMapping("/")
    public UserDto.SingleUser<? extends UserDto.Data> update(@RequestBody @Valid UserDto.UserUpdateRequest request) {
        var user = userService.update(request);

        return UserDto.SingleUser.fromEntity(user, UserDto.Data.CompleteNoPassword.class);
    }
}
