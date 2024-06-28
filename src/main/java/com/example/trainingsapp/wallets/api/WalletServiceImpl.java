package com.example.trainingsapp.wallets.api;

import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.model.User;
import com.example.trainingsapp.wallets.api.dto.WalletCreateDTO;
import com.example.trainingsapp.wallets.api.dto.WalletDTO;
import com.example.trainingsapp.wallets.api.dto.WalletUpdateDTO;
import com.example.trainingsapp.wallets.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        String walletName = createWalletDTO.getName();

        Wallet wallet = new Wallet(walletName, walletOwner);

        walletRepository.save(wallet);

        return walletModelMapper.mapWalletEntityToWalletDTO(wallet);
    }

    @Override
    public void deleteWallet(Long walletId, Long userId) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (!wallet.isPresent()) {
            throw new AppRuntimeException(ErrorCode.W001, String.format("Wallet with this id not exist", walletId));
        }
        if (!wallet.get().getUser().getId().equals(userId)) {
            throw new AppRuntimeException(ErrorCode.W002, "You don't have permissions to delete that wallet");
        }
        walletRepository.deleteById(walletId);
    }

    @Override
    public WalletDTO updateWallet(WalletUpdateDTO updateWalletDTO, Long walletId, Long userId) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (!wallet.isPresent()) {
            throw new AppRuntimeException(ErrorCode.W001, String.format("Wallet with this id not exist"));
        }
        if (!wallet.get().getUser().getId().equals(userId)) {
            throw new AppRuntimeException(ErrorCode.W002, "You don't have permissions to delete that wallet");
        }

        Wallet existedWallet = wallet.get();
        existedWallet.setName(updateWalletDTO.getName());

        walletRepository.save(existedWallet);

        return walletModelMapper.mapWalletEntityToWalletDTO(existedWallet);
    }

    @Override
    public Optional<Wallet> findById(Long walletId, Long userId) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (!wallet.isPresent()) {
            throw new AppRuntimeException(ErrorCode.W001, String.format("Wallet with this id not exist"));
        }
        if (!wallet.get().getUser().getId().equals(userId)) {
            throw new AppRuntimeException(ErrorCode.W002, "You don't have permissions to view that wallet");
        }
        return wallet;

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
