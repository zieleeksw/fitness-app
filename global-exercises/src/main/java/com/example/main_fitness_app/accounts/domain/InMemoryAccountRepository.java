package com.example.main_fitness_app.accounts.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class InMemoryAccountRepository implements AccountRepository {

    private final Map<Long, AccountEntity> database = new HashMap<>();

    @Override
    public Optional<AccountEntity> findByEmail(String email) {
        return database.values().stream()
                .filter(entity -> entity.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public AccountEntity save(AccountEntity entity) {
        long id = entity.getId();
        AccountEntity savedEntity = new AccountEntity(
                entity.getEmail(),
                entity.getPassword()
        );
        database.put(id, savedEntity);
        return savedEntity;
    }

    @Override
    public Integer count() {
        return database.size();
    }
}