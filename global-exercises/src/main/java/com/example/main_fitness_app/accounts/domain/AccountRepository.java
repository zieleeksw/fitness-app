package com.example.main_fitness_app.accounts.domain;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface AccountRepository extends Repository<AccountEntity, Long> {

    Optional<AccountEntity> findByEmail(String email);

    AccountEntity save(AccountEntity entity);

    Integer count();
}
