package com.example.trainingsapp.authorization.api;

import com.example.trainingsapp.authorization.api.dto.UserLoginDTO;
import com.example.trainingsapp.authorization.api.impl.AuthServiceImpl;
import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.api.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    @DisplayName("Should create adn save new user to the database")
    void addUser_newUser_shouldSaveUser() {
        // given
        UserDTO userDTO = new UserDTO("damian", "baziak", 30, "damianbaziak@gmail.com",
                "bazyl", "1234567890");

        when(passwordEncoder.encode("1234567890")).thenReturn("encodedPassword");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        // when
        authService.registerUser(userDTO);

        // then
        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();


        assertEquals(userDTO.getFirstname(), capturedUser.getFirstname());
        assertEquals(userDTO.getLastname(), capturedUser.getLastname());
        assertEquals(userDTO.getAge(), capturedUser.getAge());
        assertEquals(userDTO.getEmail(), capturedUser.getEmail());
        assertEquals(userDTO.getUsername(), capturedUser.getUsername());
        assertEquals("encodedPassword", capturedUser.getPassword());
    }

    @Test
    @DisplayName("Should throw an AppRuntimeException when user with the same email exists")
    void addUser_userWithEmailExists_shouldThrowException() {
        // given
        UserDTO userDTO = new UserDTO("damian", "baziak", 30, "damianbaziak@gmail.com",
                "bazyl", "1234567890");
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(
                Optional.of(User.builder().email(userDTO.getEmail()).build()));

        // when
        AppRuntimeException result = assertThrows(
                AppRuntimeException.class, () -> authService.registerUser(userDTO),
                "User with this email already exists");

        // then
        verify(userRepository, never()).save(any(User.class));
        assertEquals(result.getMessage(), ErrorCode.U001.getBusinessMessage());
    }

    @Test
    void loginUser() {
        UserLoginDTO userLoginDTO = new UserLoginDTO("damianbaziak@gmail.com",
                "1234567890");


    }

    @Test
    void hashedPassword() {
    }
}