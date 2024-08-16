package com.example.trainingsapp.authorization.api;

import com.example.trainingsapp.authorization.api.dto.UserLoginDTO;
import com.example.trainingsapp.authorization.api.impl.AuthServiceImpl;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthServiceImplTest {
    @InjectMocks
    AuthServiceImpl authService;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    AutoCloseable autoCloseable;
    User user;
    UserDTO userDTO;
    UserLoginDTO userLoginDTO;




    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");

        userLoginDTO = new UserLoginDTO("damianbaziak@gmail.com", "1234567890");

        userDTO = new UserDTO ("damian", "baziak", 30, "damianbaziak@gmail.com",
                "bazyl", "1234567890");

        user = User.builder()
                .firstname(userDTO.getFirstname())
                .lastname(userDTO.getLastname())
                .age(userDTO.getAge())
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testAddUser() {

        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = authService.registerUser(userDTO);

        assertThat(result).isNotNull();
        assertThat(result.getAge()).isEqualTo(user.getAge());
        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getPassword()).isEqualTo("encodedPassword");
    }

    @Test
    void loginUser() {


    }

    @Test
    void hashedPassword() {
    }
}