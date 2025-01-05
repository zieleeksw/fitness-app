package com.example.main_fitness_app.auth.domain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

class JwtFacade {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    boolean isValid(String token, UserDetails userDetails) {
        String email = extractEmailFrom(token);
        return email.equals(userDetails.getUsername()) && isExpired(token);
    }

    boolean isExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    String generateRefreshToken(UserDetails userDetails) {
        return buildToken(userDetails, refreshTokenExpiration);
    }

    String generateToken(UserDetails userDetails) {
        return buildToken(userDetails, jwtExpiration);
    }

    private String buildToken(UserDetails userDetails, long expiration) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(signInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    String extractEmailFrom(String token) {
        return extractClaimFrom(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaimFrom(token, Claims::getExpiration);
    }

    private <T> T extractClaimFrom(String token, Function<Claims, T> claimsResolver) {
        Claims claims = claimsFrom(token);
        return claimsResolver.apply(claims);
    }

    private Claims claimsFrom(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey signInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
