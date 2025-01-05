package com.example.main_fitness_app.auth.domain;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

class AuthManager {

    public void setAuthentication(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    public boolean isAuthenticationNotSet() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }
}
