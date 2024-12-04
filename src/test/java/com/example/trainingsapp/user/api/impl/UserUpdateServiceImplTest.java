package com.example.trainingsapp.user.api.impl;

import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.api.dto.UserEmailUptadeDTO;
import com.example.trainingsapp.user.api.dto.UserPasswordUpdateDTO;
import com.example.trainingsapp.user.api.dto.UserUsernameUpdateDTO;
import com.example.trainingsapp.user.api.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUpdateServiceImplTest {
    private static final String NEW_USERNAME = "New_Username";
    private static final Long USER_ID_1L = 1L;
    private static final int USER_AGE = 30;
    private static final String USER_FIRSTNAME = "Firstname_Example";
    private static final String USER_LASTNAME = "Lastname_Example";
    private static final String USER_EMAIL = "example@email.com";
    private static final String USER_PASSWORD = "52345hg23jk4h5";
    private static final String USER_USERNAME = "Username_Example";
    private static final String NEW_PASSWORD = "New_Password" ;
    private static final String NEW_EMAIL = "new_email@email.com" ;

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;


    @Test
    @DisplayName("Should return UserDTO with updated username when user exists")
    void updateUsername_whenUserExists_returnUserDTO() {
        // given
        UserUsernameUpdateDTO updateDTO = new UserUsernameUpdateDTO(NEW_USERNAME);
        User existingUser = User.builder().id(USER_ID_1L).firstname(USER_FIRSTNAME).lastname(USER_LASTNAME).age(USER_AGE)
                .email(USER_EMAIL).username(USER_USERNAME).password(USER_PASSWORD).build();

        UserDTO expectedUserDTO = new UserDTO(USER_FIRSTNAME, USER_LASTNAME, USER_AGE, USER_EMAIL, NEW_USERNAME, USER_PASSWORD);

        when(userRepository.findById(USER_ID_1L)).thenReturn(Optional.of(existingUser));

        // when
        UserDTO result = userService.updateUsername(USER_ID_1L, updateDTO);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(expectedUserDTO);
        verify(userRepository).save(existingUser);

    }

    @Test
    @DisplayName("Should return an AppRuntimeException when user doesn't exist")
    void updateUsername_whenUserNotExists_returnException() {
        // given
        UserUsernameUpdateDTO updateDTO = new UserUsernameUpdateDTO(NEW_PASSWORD);
        when(userRepository.findById(USER_ID_1L)).thenReturn(Optional.empty());

        // when and then
        AppRuntimeException result = assertThrows(
                AppRuntimeException.class, () -> userService.updateUsername(USER_ID_1L, updateDTO));

        assertAll(
                () -> assertEquals(result.getStatus(), ErrorCode.U003.getBusinessStatus()),
                () -> assertEquals(result.getMessage(), ErrorCode.U003.getBusinessMessage()),
                () -> assertEquals(result.getHttpStatusCode(), ErrorCode.U003.getHttpStatusCode())
        );
    }



    @Test
    @DisplayName("Should return UserDTO with updated password when user exists")
    void updatePassword_whenUserExists_returnUserDTO() {
        // given
        UserPasswordUpdateDTO updateDTO = new UserPasswordUpdateDTO(NEW_PASSWORD);
        User existingUser = User.builder().id(USER_ID_1L).firstname(USER_FIRSTNAME).lastname(USER_LASTNAME).age(USER_AGE)
                .email(USER_EMAIL).username(USER_USERNAME).password(USER_PASSWORD).build();

        UserDTO expectedUserDTO = new UserDTO(USER_FIRSTNAME, USER_LASTNAME, USER_AGE, USER_EMAIL, USER_USERNAME, NEW_PASSWORD);

        when(userRepository.findById(USER_ID_1L)).thenReturn(Optional.of(existingUser));

        // when
        UserDTO result = userService.updatePassword(USER_ID_1L, updateDTO);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(expectedUserDTO);
        verify(userRepository).save(existingUser);

    }

    @Test
    @DisplayName("Should return an AppRuntimeException when user doesn't exist")
    void updatePassword_whenUserNotExists_returnException() {
        // given
        UserPasswordUpdateDTO updateDTO = new UserPasswordUpdateDTO(NEW_PASSWORD);
        when(userRepository.findById(USER_ID_1L)).thenReturn(Optional.empty());

        // when and then
        AppRuntimeException result = assertThrows(
                AppRuntimeException.class, () -> userService.updatePassword(USER_ID_1L, updateDTO));

        assertAll(
                () -> assertEquals(result.getStatus(), ErrorCode.U003.getBusinessStatus()),
                () -> assertEquals(result.getMessage(), ErrorCode.U003.getBusinessMessage()),
                () -> assertEquals(result.getHttpStatusCode(), ErrorCode.U003.getHttpStatusCode())
        );
    }

    @Test
    @DisplayName("Should return UserDTO with updated email when user exists")
    void updateEmail_whenUserExists_returnUserDTO() {
        // given
        UserEmailUptadeDTO updateDTO = new UserEmailUptadeDTO(NEW_EMAIL);
        User existingUser = User.builder().id(USER_ID_1L).firstname(USER_FIRSTNAME).lastname(USER_LASTNAME).age(USER_AGE)
                .email(USER_EMAIL).username(USER_USERNAME).password(USER_PASSWORD).build();

        UserDTO expectedUserDTO = new UserDTO(USER_FIRSTNAME, USER_LASTNAME, USER_AGE, NEW_EMAIL, USER_USERNAME, USER_PASSWORD);

        when(userRepository.findById(USER_ID_1L)).thenReturn(Optional.of(existingUser));

        // when
        UserDTO result = userService.updateEmail(USER_ID_1L, updateDTO);

        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).usingRecursiveAssertion().isEqualTo(expectedUserDTO);
        verify(userRepository).save(existingUser);

    }

    @Test
    @DisplayName("Should return an AppRuntimeException when user doesn't exist")
    void updateEmail_whenUserNotExists_returnException() {
        // given
        UserEmailUptadeDTO updateDTO = new UserEmailUptadeDTO(NEW_PASSWORD);
        when(userRepository.findById(USER_ID_1L)).thenReturn(Optional.empty());

        // when and then
        AppRuntimeException result = assertThrows(
                AppRuntimeException.class, () -> userService.updateEmail(USER_ID_1L, updateDTO));

        assertAll(
                () -> assertEquals(result.getStatus(), ErrorCode.U003.getBusinessStatus()),
                () -> assertEquals(result.getMessage(), ErrorCode.U003.getBusinessMessage()),
                () -> assertEquals(result.getHttpStatusCode(), ErrorCode.U003.getHttpStatusCode())
        );
    }



}



