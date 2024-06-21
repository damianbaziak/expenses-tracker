package com.example.trainingsapp.wallets.api;

import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.model.MyUser;
import com.example.trainingsapp.wallets.api.dto.CreateWalletDTO;
import com.example.trainingsapp.wallets.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WalletRepository walletRepository;

    @Override
    public Wallet createWallet(CreateWalletDTO createWalletDTO, Long userId) {

        MyUser walletOwner = getUserbyUserId(userId);
        String walletName = createWalletDTO.getName();

        Wallet wallet = new Wallet(walletName, walletOwner);

        return walletRepository.save(wallet);
    }

    @Override
    public Optional<Wallet> getByName(String name) {
        return walletRepository.findByName(name);
    }

    public MyUser getUserbyUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new AppRuntimeException(ErrorCode.U003, String.format("User with id: %d doesn't exist.", userId)));
    }
}
