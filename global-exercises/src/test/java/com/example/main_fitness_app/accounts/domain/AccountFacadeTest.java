package com.example.main_fitness_app.accounts.domain;

import com.example.main_fitness_app.accounts.AccountException;
import com.example.main_fitness_app.accounts.dto.AccountCandidate;
import com.example.main_fitness_app.accounts.dto.AccountDto;
import com.example.main_fitness_app.exercises.dto.MuscleUsageDto;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AccountFacadeTest {

    private final AccountFacade facade = new AccountConfiguration().accountFacade();

    @Test
    void shouldAddNewAccount() {
        AccountCandidate accountDto = new AccountCandidate(
                "admin@gmail.com",
                "secret_password"
        );

        AccountDto result = facade.add(accountDto);

        assertNotNull(result);
        assertEquals("admin@gmail.com", result.email());
        assertEquals("secret_password", result.password());
    }

    @Test
    void shouldThrowExceptionWhenAccountAlreadyExists() {
        AccountCandidate accountDto = new AccountCandidate(
                "admin@gmail.com",
                "secret_password"
        );

        facade.add(accountDto);

        AccountException accountException = assertThrows(AccountException.class, () -> facade.add(accountDto));
        assertEquals("Account with the email: admin@gmail.com already exists", accountException.getMessage());
    }

    @Test
    void shouldFindExerciseByEmail() {
        Set<MuscleUsageDto> muscleUsages = Set.of(
                new MuscleUsageDto("PECTORAL_MAJOR", "HIGH"),
                new MuscleUsageDto("TRICEPS", "MEDIUM")
        );

        facade.add(new AccountCandidate("admin2@gmail.com", "secret_password"));

        AccountDto result = facade.findByEmail("admin2@gmail.com");

        assertNotNull(result);
        assertEquals("admin2@gmail.com", result.email());
        assertEquals("secret_password", result.password());
    }
}