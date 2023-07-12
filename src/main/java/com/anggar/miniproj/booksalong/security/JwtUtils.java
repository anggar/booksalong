package com.anggar.miniproj.booksalong.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtils {

    private final long validMs;
    private final Key key;

    public JwtUtils(String signKey, long validMs) {
        this.validMs = validMs;
        this.key = Keys.hmacShaKeyFor(signKey.getBytes(StandardCharsets.UTF_8));
    }

    public String encode(String sub) {
        if (sub == null || sub.isBlank()) {
            return null;
        }

        var exp = Instant.now();
        return Jwts.builder()
                .setSubject(sub)
                .setIssuedAt(new Date(exp.toEpochMilli()))
                .setExpiration(new Date(exp.toEpochMilli() + validMs))
                .signWith(key)
                .compact();
    }

    public boolean isValidToken(String jwt) {
        try {
            var jwtParser = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build();
            var claims = jwtParser.parseClaimsJws(jwt).getBody();
            var exp = claims.getExpiration();

            return exp.after(Date.from(Instant.now()));
        } catch (JwtException e) {
            return false;
        }
    }

    public String getSub(String jwt) {
        try {
            var jwtParser = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build();
            var claims = jwtParser.parseClaimsJws(jwt);

            return claims.getBody().getSubject();
        } catch (JwtException e) {
            return null;
        }
    }
}
