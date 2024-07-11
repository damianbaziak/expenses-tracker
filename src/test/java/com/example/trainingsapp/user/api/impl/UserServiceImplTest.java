package com.example.trainingsapp.user.api.impl;

import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.dto.UsernameUpdateDTO;
import com.example.trainingsapp.user.model.User;
import com.example.trainingsapp.wallet.model.Wallet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    // To pole służy do zarządzania zasobami, które muszą zostać zamknięte po zakończeniu testu. W kontekście Mockito, może być używane do automatycznego zamknięcia zasobów po zakończeniu testów, szczególnie przy użyciu MockitoAnnotations.openMocks(this).
    //Pozwala to na automatyczne wyczyszczenie mocków i uniknięcie wycieków pamięci po zakończeniu testów.
    AutoCloseable autoCloseable;
    User user;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        List<Wallet> wallets = new ArrayList<>();

        user = new User(1L, "damian", "baziak", 30, "damianbaziak@gmail.com",
                "bazyl", "1234567890", wallets  );


    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void TestGetUserById() {
        mock(User.class);
        mock(UserRepository.class);
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        Optional<User> result = userService.getUserById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void TestUpdateUsername() {
        mock(User.class);
        mock(UserRepository.class);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        UsernameUpdateDTO usernameUpdateDTO = new UsernameUpdateDTO("bazooka");

        User result = userService.updateUsername(user.getId(), usernameUpdateDTO);

        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getUsername()).isEqualTo(usernameUpdateDTO.getUsername());
    }

    @Test
    void updatePassword() {
    }

    @Test
    void updateEmail() {
    }
}