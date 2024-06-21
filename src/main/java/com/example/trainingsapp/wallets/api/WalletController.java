package com.example.trainingsapp.wallets.api;

import com.example.trainingsapp.wallets.api.dto.CreateWalletDTO;
import com.example.trainingsapp.wallets.model.Wallet;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("api/wallets")
public class WalletController {
    @Autowired
    WalletServiceImpl walletService;

    @PostMapping("/create")
    public ResponseEntity createWallet(@Valid @RequestBody CreateWalletDTO createWalletDTO, Principal principal) {
        Optional<Wallet> walletFromDb = walletService.getByName(createWalletDTO.getName());

        Long userID = Long.valueOf(principal.getName());

        Wallet wallet = walletService.createWallet(createWalletDTO, userID);

        return new ResponseEntity<>(wallet, HttpStatus.CREATED);


    }
}
