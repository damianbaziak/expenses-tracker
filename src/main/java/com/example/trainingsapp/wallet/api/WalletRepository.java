package com.example.trainingsapp.wallet.api;

import com.example.trainingsapp.wallet.model.Wallet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends CrudRepository<Wallet, Long> {
    Optional<Wallet> findByName(String name);

    List<Wallet> findAllByUserIdOrderByNameAsc(Long userId);

    Optional<Wallet> findByIdAndUserId(Long walletId, Long userId);
}
