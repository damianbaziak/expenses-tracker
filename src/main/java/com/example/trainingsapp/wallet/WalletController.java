package com.example.trainingsapp.wallet;

import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.model.User;
import com.example.trainingsapp.wallet.api.WalletService;
import com.example.trainingsapp.wallet.api.dto.WalletCreateDTO;
import com.example.trainingsapp.wallet.api.dto.WalletDTO;
import com.example.trainingsapp.wallet.api.dto.WalletUpdateDTO;
import com.example.trainingsapp.wallet.model.Wallet;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WalletService walletService;

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    @PostMapping()
    public ResponseEntity<WalletDTO> createWallet(@Valid @RequestBody WalletCreateDTO createWalletDTO, Principal principal) {

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

    @PatchMapping("/{id}")
    public ResponseEntity<WalletDTO> updateWallet(@Min(1) @NotNull @PathVariable Long id, @Valid @RequestBody WalletUpdateDTO walletUpdateDTO,
                                       Principal principal) {

        System.out.println("Received request to update wallet with id: " + id);

        String email = principal.getName();
        Optional<User> user = userRepository.findByEmail(email);

        Long userId = user.get().getId();

        WalletDTO walletDTO = walletService.updateWallet(id, walletUpdateDTO, userId);

        return new ResponseEntity<>(walletDTO, HttpStatus.OK);

    }

    @GetMapping("/")
    public ResponseEntity<List<Wallet>> getWallets(Principal principal) {
        String email = principal.getName();
        Optional<User> user = userRepository.findByEmail(email);

        Long userId = user.get().getId();

        List<Wallet> wallets = walletService.getWallets(userId);

        return new ResponseEntity<>(wallets, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletDTO> getWalletById(@Min(1) @NotNull @PathVariable Long id, Principal principal) {
        String email = principal.getName();
        Optional<User> user = userRepository.findByEmail(email);

        Long userId = user.get().getId();

        WalletDTO walletDTO = walletService.findById(id, userId);

        return new ResponseEntity<>(walletDTO, HttpStatus.OK);

    }
}
