package com.example.trainingsapp.wallets.api;

import com.example.trainingsapp.wallets.api.dto.CreateWalletDTO;
import com.example.trainingsapp.wallets.api.dto.WalletDTO;
import com.example.trainingsapp.wallets.model.Wallet;

import java.util.Optional;

public interface WalletService {
    WalletDTO createWallet(CreateWalletDTO createWalletDTO, Long userId);
    //Optional<Wallet> getByName(String name);
    void deleteWallet(Long walletId, Long userId);
}
