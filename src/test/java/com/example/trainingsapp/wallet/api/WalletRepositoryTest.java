package com.example.trainingsapp.wallet.api;

import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.model.User;
import com.example.trainingsapp.wallet.model.Wallet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
public class WalletRepositoryTest {

    @Autowired
    WalletRepository walletRepository;
    @Autowired
    UserRepository userRepository;
    Wallet wallet;
    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .firstname("damian")
                .lastname("baziak")
                .age(30)
                .email("damianbaziak@gmail.com")
                .username("bazyl")
                .password("1234567890")
                .build();
        userRepository.save(user);

        wallet = Wallet.builder()
                .user(user)
                .name("slodycze")
                .creationDate(Instant.now())
                .build();
        walletRepository.save(wallet);
    }

    @AfterEach
    void tearDown() {
        walletRepository.deleteAll();
        userRepository.deleteAll();
    }


    // Test case SUCCESS

    @Test
    void testFindWalletsByUserId_Found() {
        System.out.println(user.getId());
        List<Wallet> wallets = walletRepository.findWalletsByUserId(user.getId());

        assertThat(wallets.get(0).getId()).isEqualTo(wallet.getId());
    }

    // Test Case FAILURE

    @Test
    void testFindWalletsByUserId_NotFound() {
        System.out.println(user.getId());
        List<Wallet> wallets = walletRepository.findWalletsByUserId(2L);


        assertThat(wallets.isEmpty()).isTrue();

    }
}


