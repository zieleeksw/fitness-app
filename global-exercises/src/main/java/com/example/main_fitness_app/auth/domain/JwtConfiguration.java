package com.example.main_fitness_app.auth.domain;

import com.example.main_fitness_app.accounts.domain.AccountFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
class JwtConfiguration {

    private final AccountFacade accountFacade;
    private final AuthenticationConfiguration configuration;

    JwtConfiguration(AccountFacade accountFacade, AuthenticationConfiguration configuration) {
        this.accountFacade = accountFacade;
        this.configuration = configuration;
    }

    @Bean
    JwtFacade jwtFacade() {
        return new JwtFacade();
    }

    @Bean
    AuthFacade authFacade() throws Exception {
        return new AuthFacade(
                jwtFacade(),
                accountFacade,
                authenticationManager()
        );
    }

    @Bean
    AccountDetailsService accountDetailsService() {
        return new AccountDetailsService(accountFacade);
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenProcessor(), authManager());
    }

    @Bean
    AuthManager authManager() {
        return new AuthManager();
    }

    @Bean
    com.example.main_fitness_app.auth.domain.TokenProcessor tokenProcessor() {
        return new TokenProcessor(
                jwtFacade(),
                new AccountDetailsService(accountFacade)
        );
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(accountDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/api/v1/auth/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

