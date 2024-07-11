package com.example.trainingsapp.wallet.impl;

import com.example.trainingsapp.user.model.User;
import com.example.trainingsapp.wallet.api.WalletRepository;
import com.example.trainingsapp.wallet.model.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class WalletGetServiceImplTest {
    @InjectMocks
    private WalletServiceImpl walletService;
    @Mock
    private WalletRepository walletRepository;
    User user;
    Wallet wallet;
    Wallet wallet2;
    AutoCloseable autoCloseable;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        wallet = Wallet.builder()
                .user(user)
                .name("slodycze")
                .creationDate(Instant.now())
                .build();

        wallet2 = Wallet.builder()
                .user(user)
                .name("zakupy")
                .creationDate(Instant.now())
                .build();
    }

    @Test
    void testGetWallets(){
        when(walletRepository.findWalletsByUserId(1L)).thenReturn(Arrays.asList(wallet,wallet2));

        List<Wallet> result = walletService.getWallets(1L);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(walletService.getWallets(1L).get(0).getName()).isEqualTo(wallet.getName());
        assertThat(walletService.getWallets(1L).get(1).getName()).isEqualTo(wallet2.getName());
    }

}
