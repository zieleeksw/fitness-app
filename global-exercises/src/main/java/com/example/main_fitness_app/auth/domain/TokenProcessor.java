package com.example.main_fitness_app.auth.domain;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

class TokenProcessor {

    private final JwtFacade jwtFacade;
    private final UserDetailsService userDetailsService;

    public TokenProcessor(JwtFacade jwtFacade, UserDetailsService userDetailsService) {
        this.jwtFacade = jwtFacade;
        this.userDetailsService = userDetailsService;
    }

    public UserDetails process(String token) {
        String email = jwtFacade.extractEmailFrom(token);
        if (email == null) {
            return null;
        }
        return userDetailsService.loadUserByUsername(email);
    }

    public boolean isValid(String token, UserDetails userDetails) {
        return jwtFacade.isValid(token, userDetails);
    }
}
