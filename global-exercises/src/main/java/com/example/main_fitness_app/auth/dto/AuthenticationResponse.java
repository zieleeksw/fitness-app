package com.example.main_fitness_app.auth.dto;

public record AuthenticationResponse(
        String token,
        String refreshToken
) {
}
