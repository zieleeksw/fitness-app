package com.example.main_fitness_app.auth.domain;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProcessor tokenProcessor;
    private final AuthManager authManager;

    JwtAuthenticationFilter(TokenProcessor tokenProcessor, AuthManager authManager) {
        this.tokenProcessor = tokenProcessor;
        this.authManager = authManager;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (hasTokenInHeader(header)) {
            processToken(header);
        }

        filterChain.doFilter(request, response);
    }

    private void processToken(String header) {
        String jwt = extractJwtFrom(header);
        UserDetails userDetails = tokenProcessor.process(jwt);

        if (userDetails != null && authManager.isAuthenticationNotSet() && tokenProcessor.isValid(jwt, userDetails)) {
            authManager.setAuthentication(userDetails);
        }
    }

    private boolean hasTokenInHeader(String header) {
        return header != null && header.startsWith("Bearer ");
    }

    private String extractJwtFrom(String header) {
        return header.replace("Bearer ", "");
    }
}