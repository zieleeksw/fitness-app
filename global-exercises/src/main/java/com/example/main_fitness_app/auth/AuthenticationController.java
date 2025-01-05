package com.example.main_fitness_app.auth;

import com.example.main_fitness_app.auth.domain.AuthFacade;
import com.example.main_fitness_app.auth.dto.AuthenticationRequest;
import com.example.main_fitness_app.auth.dto.AuthenticationResponse;
import com.example.main_fitness_app.auth.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController {

    private final AuthFacade authFacade;

    AuthenticationController(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @PostMapping("/register")
    ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse register = authFacade.register(request);
        return ResponseEntity.ok(register);
    }

    @PostMapping("/authenticate")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticate = authFacade.authenticate(request);
        return ResponseEntity.ok(authenticate);
    }
}
