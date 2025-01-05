package com.example.main_fitness_app.auth.dto;

public record AuthenticationRequest(
        String email,
        String password
) {
}
