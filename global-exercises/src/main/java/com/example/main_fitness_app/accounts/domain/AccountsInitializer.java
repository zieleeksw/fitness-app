package com.example.main_fitness_app.accounts.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

class AccountsInitializer {

    private final AccountRepository repository;
    private final PasswordEncoder passwordEncoder;

    AccountsInitializer(AccountRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    void init() {
        if (repository.count() == 0) {
            repository.save(new AccountEntity(
                    "admin@gmail.com",
                    passwordEncoder.encode("password")
            ));
            repository.save(new AccountEntity(
                    "user@gmail.com",
                    passwordEncoder.encode("password")
            ));
        }
    }
}
