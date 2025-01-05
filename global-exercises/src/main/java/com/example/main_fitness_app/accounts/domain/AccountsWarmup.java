package com.example.main_fitness_app.accounts.domain;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
class AccountsWarmup implements ApplicationListener<ContextRefreshedEvent> {

    private final AccountsInitializer initializer;

    AccountsWarmup(@Qualifier("accountRepository") AccountRepository repository, PasswordEncoder encoder) {
        this.initializer = new AccountsInitializer(repository, encoder);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initializer.init();
    }
}
