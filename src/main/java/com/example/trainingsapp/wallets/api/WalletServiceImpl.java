package com.example.trainingsapp.wallets.api;

import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.model.User;
import com.example.trainingsapp.wallets.api.dto.CreateWalletDTO;
import com.example.trainingsapp.wallets.api.dto.WalletDTO;
import com.example.trainingsapp.wallets.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletModelMapper walletModelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WalletRepository walletRepository;

    @Override
    public WalletDTO createWallet (CreateWalletDTO createWalletDTO, Long userId) {

        User walletOwner = getUserbyUserId(userId);
        String walletName = createWalletDTO.getName();

        Wallet wallet = new Wallet(walletName, walletOwner);
        walletRepository.save(wallet);

        return walletModelMapper.mapWalletEntityToWalletDTO(wallet);
    }

    @Override
    public void deleteWallet(Long walletId, Long userId) {
        walletRepository.deleteById(walletId);
    }

    //@Override
    //public Optional<Wallet> getByName(String name) {
    //    return walletRepository.findByName(name);
    //}

    public User getUserbyUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new AppRuntimeException(ErrorCode.U003, String.format("User with id: %d doesn't exist.", userId)));
    }
}
