package com.example.main_fitness_app.auth.domain;

import com.example.main_fitness_app.accounts.dto.AccountDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

class AccountDetails implements UserDetails {

    private final AccountDto account;

    AccountDetails(AccountDto account) {
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return account.password();
    }

    @Override
    public String getUsername() {
        return account.email();
    }
}
