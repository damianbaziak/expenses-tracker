package com.example.trainingsapp.wallet.impl;

import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.model.User;
import com.example.trainingsapp.wallet.api.WalletModelMapper;
import com.example.trainingsapp.wallet.api.WalletRepository;
import com.example.trainingsapp.wallet.api.WalletService;
import com.example.trainingsapp.wallet.api.dto.WalletCreateDTO;
import com.example.trainingsapp.wallet.api.dto.WalletDTO;
import com.example.trainingsapp.wallet.api.dto.WalletUpdateDTO;
import com.example.trainingsapp.wallet.api.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletModelMapper walletModelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public WalletDTO createWallet(WalletCreateDTO createWalletDTO, Long userId) {
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
            throw new AppRuntimeException(ErrorCode.W002, "You don't have permissions to update that wallet");
        }

        Wallet existedWallet = wallet.get();
        existedWallet.setName(updateWalletDTO.getName());

        walletRepository.save(existedWallet);

        return walletModelMapper.mapWalletEntityToWalletDTO(existedWallet);
    }

    @Override
    public WalletDTO findById(Long walletId, Long userId) {
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if (!wallet.isPresent()) {
            throw new AppRuntimeException(ErrorCode.W001, String.format("Wallet with this id: %d not exist", walletId));
        }
        if (!wallet.get().getUser().getId().equals(userId)) {
            throw new AppRuntimeException(ErrorCode.W002, "You don't have permissions to view that wallet");
        }

        Wallet existedWallet = wallet.get();

        return new WalletDTO(existedWallet.getId(), existedWallet.getName(), existedWallet.getCreationDate(),
                existedWallet.getUser().getId());
    }

    @Override
    public List<WalletDTO> getWallets(Long userId) {
        List<Wallet> walletList = walletRepository.findAllByUserIdOrderByNameAsc(userId);

        if (walletList.isEmpty()) {
            throw new AppRuntimeException(ErrorCode.W001, "You have no wallets");
        }

        return walletList.stream()
                .map(wallet -> new WalletDTO(wallet.getId(), wallet.getName(), wallet.getCreationDate(), wallet.getUser()
                        .getId()))
                .toList();
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new AppRuntimeException(ErrorCode.U003, String.format("User with id: %d doesn't exist.", userId)));
    }
}
