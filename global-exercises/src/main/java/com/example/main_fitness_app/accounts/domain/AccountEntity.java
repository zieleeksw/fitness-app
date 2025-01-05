package com.example.main_fitness_app.accounts.domain;

import com.example.main_fitness_app.accounts.dto.AccountCandidate;
import com.example.main_fitness_app.accounts.dto.AccountDto;
import jakarta.persistence.*;

@Entity(name = "accounts")
class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    AccountEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }

    protected AccountEntity() {
    }

    static AccountEntity from(AccountCandidate candidate) {
        return new AccountEntity(
                candidate.email(),
                candidate.password()
        );
    }

    long getId() {
        return id;
    }

    String getEmail() {
        return this.email;
    }

    String getPassword() {
        return this.password;
    }

    AccountDto dto() {
        return new AccountDto(
                this.id,
                this.email,
                this.password
        );
    }
}
