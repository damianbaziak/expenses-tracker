package com.example.trainingsapp.wallets.api;

import com.example.trainingsapp.wallets.api.dto.WalletCreateDTO;
import com.example.trainingsapp.wallets.api.dto.WalletUpdateDTO;
import com.example.trainingsapp.wallets.api.dto.WalletDTO;
import com.example.trainingsapp.wallets.model.Wallet;

import java.util.Optional;

public interface WalletService {
    WalletDTO createWallet(WalletCreateDTO createWalletDTO, Long userId);
    //Optional<Wallet> getByName(String name);
    void deleteWallet(Long walletId, Long userId);

    WalletDTO updateWallet(WalletUpdateDTO updateWalletDTO, Long walletId, Long userId);

    Optional<Wallet> findById(Long walletId, Long UserId);

}
