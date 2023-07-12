package com.anggar.miniproj.booksalong.config;

import com.anggar.miniproj.booksalong.security.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtUtilsConfiguration {

    @Bean
    public JwtUtils getJwtUtils(
        @Value("${app.auth.token.sign-key}") String signKey,
        @Value("${app.auth.token.valid-ms}") Long validMs
    ) throws Exception {
        if (signKey.length() < 32) {
            throw new Exception("minimum sign key length is 32");
        } else {
            return new JwtUtils(signKey, validMs);
        }
    }
}
