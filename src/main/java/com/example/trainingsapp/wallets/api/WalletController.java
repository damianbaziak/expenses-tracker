package com.example.trainingsapp.wallets.api;

import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.model.User;
import com.example.trainingsapp.wallets.api.dto.WalletCreateDTO;
import com.example.trainingsapp.wallets.api.dto.WalletDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("api/wallets")
public class WalletController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    WalletServiceImpl walletService;

    @PostMapping()
    public ResponseEntity createWallet(@Valid @RequestBody WalletCreateDTO createWalletDTO, Principal principal) {
        String email = principal.getName();
        Optional<User> user = userRepository.findByEmail(email);

        Long userID = user.get().getId();

        WalletDTO walletDTO = walletService.createWallet(createWalletDTO, userID);

        return new ResponseEntity<>(walletDTO, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWallet(@Min(1) @NotNull @PathVariable Long id, Principal principal) {
        String email = principal.getName();
        Optional<User> user = userRepository.findByEmail(email);

        Long userId = user.get().getId();

        walletService.deleteWallet(id, userId);

        return new ResponseEntity<>("Wallet deleted successfully", HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity


}
