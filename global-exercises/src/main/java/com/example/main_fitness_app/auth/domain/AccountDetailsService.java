package com.example.main_fitness_app.auth.domain;

import com.example.main_fitness_app.accounts.domain.AccountFacade;
import com.example.main_fitness_app.accounts.dto.AccountDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class AccountDetailsService implements UserDetailsService {

    private final AccountFacade facade;

    AccountDetailsService(AccountFacade facade) {
        this.facade = facade;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountDto account = facade.findByEmail(username);
        return new AccountDetails(account);
    }
}
