package com.example.trainingsapp.wallet.api;

import com.example.trainingsapp.wallet.api.dto.WalletCreateDTO;
import com.example.trainingsapp.wallet.api.dto.WalletDTO;
import com.example.trainingsapp.wallet.api.dto.WalletUpdateDTO;
import com.example.trainingsapp.wallet.model.Wallet;

import java.util.List;

public interface WalletService {
    WalletDTO createWallet(WalletCreateDTO createWalletDTO, Long userId);

    void deleteWallet(Long walletId, Long userId);

    WalletDTO updateWallet(Long walletId, WalletUpdateDTO updateWalletDTO, Long userId);

    WalletDTO findById(Long walletId, Long UserId);

    List<WalletDTO> getWallets(Long userId);

}
