package com.example.trainingsapp.user.api;

import com.example.trainingsapp.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

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

        assertThat(userFromDb.get().getId()).isEqualTo(user.getId());
    }
}
