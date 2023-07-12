package com.anggar.miniproj.booksalong.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AuthenticationProvider {

    @Autowired
    private final UserDetailsService userDetailsService;

    public Authentication getAuthentication(String username) {
        return Optional.ofNullable(username)
                .map(userDetailsService::loadUserByUsername)
                .map(userDetails -> new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                ))
                .orElse(null);
    }
}
