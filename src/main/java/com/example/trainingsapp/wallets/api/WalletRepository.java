package com.example.trainingsapp.wallets.api;

import com.example.trainingsapp.wallets.model.Wallet;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WalletRepository extends CrudRepository<Wallet, Long> {
    Optional<Wallet> findByName(String name);
}
