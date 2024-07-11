package com.example.trainingsapp.wallet.impl;

import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.model.User;
import com.example.trainingsapp.wallet.api.WalletRepository;
import com.example.trainingsapp.wallet.model.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.Mockito.*;

class WalletDeleteServiceImplTest {

    @InjectMocks
    private WalletServiceImpl walletService;
    @Mock
    private WalletRepository walletRepository;
    @Mock
    private UserRepository userRepository;
    Wallet wallet;
    User user;
    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        user = User.builder()
                .id(1L)
                .firstname("damian")
                .lastname("baziak")
                .age(30)
                .email("damianbaziak@gmail.com")
                .username("bazyl")
                .password("1234567890")
                .build();

        wallet = Wallet.builder()
                .user(user)
                .name("slodycze")
                .creationDate(Instant.now())
                .build();


    }

    @Test
    void deleteWallet() {
        when(walletRepository.findById(1L)).thenReturn(Optional.of(wallet));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        walletService.deleteWallet(1L, user.getId());

        verify(walletRepository, times(1)).deleteById(1L);

    }







}