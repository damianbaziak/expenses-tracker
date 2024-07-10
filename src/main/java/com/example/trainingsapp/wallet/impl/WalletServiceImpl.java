package com.example.trainingsapp.wallet.impl;

import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.model.User;
import com.example.trainingsapp.wallet.api.WalletModelMapper;
import com.example.trainingsapp.wallet.api.WalletRepository;
import com.example.trainingsapp.wallet.api.WalletService;
import com.example.trainingsapp.wallet.api.dto.WalletCreateDTO;
import com.example.trainingsapp.wallet.api.dto.WalletDTO;
import com.example.trainingsapp.wallet.api.dto.WalletUpdateDTO;
import com.example.trainingsapp.wallet.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletModelMapper walletModelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WalletRepository walletRepository;

    @Override
    public WalletDTO createWallet (WalletCreateDTO createWalletDTO, Long userId) {

        User walletOwner = getUserByUserId(userId);
        String walletName = createWalletDTO.name();

        Wallet wallet = new Wallet(walletName, walletOwner);

        walletRepository.save(wallet);

        return walletModelMapper.mapWalletEntityToWalletDTO(wallet);
    }

    @Override
    public void deleteWallet(Long walletId, Long userId) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (!wallet.isPresent()) {
            throw new AppRuntimeException(ErrorCode.W001, String.format("Wallet with this id: %d not exist", walletId));
        }
        if (!wallet.get().getUser().getId().equals(userId)) {
            throw new AppRuntimeException(ErrorCode.W002, "You don't have permissions to delete that wallet");
        }
        walletRepository.deleteById(walletId);
    }

    @Override
    public WalletDTO updateWallet(Long walletId, WalletUpdateDTO updateWalletDTO, Long userId) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (!wallet.isPresent()) {
            throw new AppRuntimeException(ErrorCode.W001, String.format("Wallet with this id: %d not exist", walletId));
        }
        if (!wallet.get().getUser().getId().equals(userId)) {
            throw new AppRuntimeException(ErrorCode.W002, "You don't have permissions to delete that wallet");
        }

        Wallet existedWallet = wallet.get();
        existedWallet.setName(updateWalletDTO.name());

        walletRepository.save(existedWallet);

        return walletModelMapper.mapWalletEntityToWalletDTO(existedWallet);
    }

    @Override
    public Optional<Wallet> findById(Long walletId, Long userId) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (!wallet.isPresent()) {
            throw new AppRuntimeException(ErrorCode.W001, String.format("Wallet with this id: %d not exist", walletId));
        }
        if (!wallet.get().getUser().getId().equals(userId)) {
            throw new AppRuntimeException(ErrorCode.W002, "You don't have permissions to view that wallet");
        }
        return wallet;
    }

    @Override
    public List<Wallet> getWallets(Long userId) {
        List<Wallet> walletList = walletRepository.findWalletsByUserId(userId);

        if (walletList.isEmpty()) {
            throw new AppRuntimeException(ErrorCode.W001, "You have no wallets");
        }

        return walletList;
    }

    //@Override
    //public Optional<Wallet> getByName(String name) {
    //    return walletRepository.findByName(name);
    //}

    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new AppRuntimeException(ErrorCode.U003, String.format("User with id: %d doesn't exist.", userId)));
    }
}
