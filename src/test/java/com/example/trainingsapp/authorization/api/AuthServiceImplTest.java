package com.example.trainingsapp.authorization.api;

import com.example.trainingsapp.authorization.api.impl.AuthServiceImpl;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class AuthServiceImplTest {
    @Mock
    UserRepository userRepository;
    AuthServiceImpl authService;
    AutoCloseable autoCloseable;
    User user;
    PasswordEncoder passwordEncoder;
    UserDTO userDTO;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        authService = new AuthServiceImpl(userRepository);
        userDTO = new UserDTO ("damian", "baziak", 30, "damianbaziak@gmail.com",
                "bazyl", "1234567890", "USER");

        user = User.builder()
                .password(hashedPassword(userDTO.getPassword()))
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testAddUser() {

        when(userRepository.save(user)).thenReturn(user);
        assertThat(authService.addUser(userDTO)).isEqualTo("Success");
    }

    public String hashedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Test
    void loginUser() {
    }

    @Test
    void hashedPassword() {
    }
}