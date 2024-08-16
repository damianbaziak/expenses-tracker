package com.example.trainingsapp.user.api;

import com.example.trainingsapp.financialtransaktioncategory.model.FinancialTransactionCategory;
import com.example.trainingsapp.user.model.User;
import com.example.trainingsapp.wallet.model.Wallet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {
        List<Wallet> wallets = new ArrayList<>();
        List<FinancialTransactionCategory> financialTransactionCategories = new ArrayList<>();
        user = new User(1L, "damian", "baziak", 30, "damianbaziak@gmail.com",
                "bazyl", "1234567890", wallets, financialTransactionCategories);

        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        user = null;
        userRepository.deleteAll();
    }

    // Test case SUCCESS

    @Test
    void testFindByUsername_Found() {
        Optional<User> userFromDb = userRepository.findByUsername("bazyl");
        assertThat(userFromDb).isPresent();

        assertThat(userFromDb.get().getId()).isEqualTo(user.getId());
        assertThat(userFromDb.get().getEmail()).isEqualTo(user.getEmail());
    }

    // TEST case FAILURED

    @Test
    void testFindByUsername_NotFound() {
        Optional<User> userFromDb = userRepository.findByUsername("okar");

        assertThat(userFromDb.isEmpty()).isTrue();
    }
}
