package com.example.main_fitness_app.auth.domain;

import com.example.main_fitness_app.accounts.domain.AccountFacade;
import com.example.main_fitness_app.accounts.dto.AccountCandidate;
import com.example.main_fitness_app.accounts.dto.AccountDto;
import com.example.main_fitness_app.auth.dto.AuthenticationRequest;
import com.example.main_fitness_app.auth.dto.AuthenticationResponse;
import com.example.main_fitness_app.auth.dto.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

public class AuthFacade {

    private final JwtFacade jwtFacade;
    private final AccountFacade accountFacade;
    private final AuthenticationManager authenticationManager;

    public AuthFacade(JwtFacade jwtFacade,
                      AccountFacade accountFacade,
                      AuthenticationManager authenticationManager) {
        this.jwtFacade = jwtFacade;
        this.accountFacade = accountFacade;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        AccountCandidate candidate = new AccountCandidate(
                request.email(),
                request.password()
        );

        AccountDto account = accountFacade.add(candidate);
        AccountDetails accountDetails = new AccountDetails(account);
        String accessToken = jwtFacade.generateToken(accountDetails);
        String refreshToken = jwtFacade.generateRefreshToken(accountDetails);
        return new AuthenticationResponse(
                accessToken,
                refreshToken
        );
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        );

        authenticate(authToken);

        AccountDto accountDto = accountFacade.findByEmail(request.email());
        AccountDetails accountDetails = new AccountDetails(accountDto);
        String accessToken = jwtFacade.generateToken(accountDetails);
        String refreshToken = jwtFacade.generateRefreshToken(accountDetails);
        return new AuthenticationResponse(
                accessToken,
                refreshToken
        );
    }

    private void authenticate(UsernamePasswordAuthenticationToken authToken) {
        authenticationManager.authenticate(authToken);
    }
}
