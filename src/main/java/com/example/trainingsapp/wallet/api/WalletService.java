package com.example.trainingsapp.wallet.api;

import com.example.trainingsapp.wallet.api.dto.WalletCreateDTO;
import com.example.trainingsapp.wallet.api.dto.WalletUpdateDTO;
import com.example.trainingsapp.wallet.api.dto.WalletDTO;
import com.example.trainingsapp.wallet.model.Wallet;

import java.util.List;
import java.util.Optional;

public interface WalletService {
    WalletDTO createWallet(WalletCreateDTO createWalletDTO, Long userId);
    //Optional<Wallet> getByName(String name);
    void deleteWallet(Long walletId, Long userId);

    WalletDTO updateWallet(Long walletId, WalletUpdateDTO updateWalletDTO, Long userId);

    Optional<WalletDTO> findById(Long walletId, Long UserId);

    List<Wallet> getWallets(Long userId);

}
