package com.example.main_fitness_app.accounts.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AccountConfiguration {

    @Bean
    AccountFacade accountFacade(AccountRepository accountRepository) {
        return new AccountFacade(accountRepository);
    }

    AccountFacade accountFacade() {
        return accountFacade(new InMemoryAccountRepository());
    }
}
