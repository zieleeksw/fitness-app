package com.example.main_fitness_app.accounts.domain;

import com.example.main_fitness_app.accounts.AccountException;
import com.example.main_fitness_app.accounts.dto.AccountCandidate;
import com.example.main_fitness_app.accounts.dto.AccountDto;

public class AccountFacade {

    private final AccountRepository repository;

    public AccountFacade(AccountRepository repository) {
        this.repository = repository;
    }

    public AccountDto findByEmail(String email) {
        return repository.findByEmail(email)
                .map(AccountEntity::dto)
                .orElseThrow(() -> new AccountException(String.format("Cannot find user with email: %s", email)));
    }

    public AccountDto add(AccountCandidate candidate) {
        if (repository.findByEmail(candidate.email()).isPresent()) {
            throw new AccountException("Account with the email: " + candidate.email() + " already exists");
        }

        AccountEntity entity = AccountEntity.from(candidate);
        AccountEntity savedEntity = repository.save(entity);
        return savedEntity.dto();
    }
}
