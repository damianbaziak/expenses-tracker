package com.example.trainingsapp.financialtransaction;

import com.example.trainingsapp.financialtransaction.api.FinancialTransactionService;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionCreateDTO;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionDTO;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionUpdateDTO;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
@RequestMapping("/api/transactions")
public class FinancialTransactionController {

    @Autowired
    FinancialTransactionService financialTransactionService;

    @Autowired
    UserRepository userRepository;

    @PostMapping()
    public ResponseEntity<FinancialTransactionDTO> createFinancialTransaction(
            @Valid @RequestBody FinancialTransactionCreateDTO financialTransactionCreateDTO, Principal principal) {
        String email = principal.getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userID = user.get().getId();

        FinancialTransactionDTO financialTransactionDTO =
                financialTransactionService.createFinancialTransaction(financialTransactionCreateDTO, userID);

        return new ResponseEntity<>(financialTransactionDTO, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<FinancialTransactionDTO>> getFinancialTransactionsByWalletId(
            @RequestParam @Min(1) @NotNull Long walletId, Principal principal) {
        String email = principal.getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userID = user.get().getId();

        List<FinancialTransactionDTO> financialTransactionDTOS = financialTransactionService
                .getFinancialTransactionsByWalletId(walletId, userID);

        return new ResponseEntity<>(financialTransactionDTOS, HttpStatus.OK);


    }

    @PatchMapping("/{id}")
    public ResponseEntity<FinancialTransactionDTO> updateTransactionById(
            @Min(1) @NotNull @PathVariable Long id,
            @Valid @RequestBody FinancialTransactionUpdateDTO financialTransactionUpdateDTO, Principal principal) {
        String email = principal.getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = user.get().getId();

        FinancialTransactionDTO financialTransactionDTO = financialTransactionService.updateFinancialTransaction(
                id, financialTransactionUpdateDTO, userId);

        return new ResponseEntity<>(financialTransactionDTO, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<FinancialTransactionDTO> getTransactionById(
            @Min(1) @NotNull @PathVariable Long id, Principal principal) {
        String email = principal.getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = user.get().getId();

        FinancialTransactionDTO financialTransactionDTO = financialTransactionService.findFinancialTransactionForUser(
                id, userId);

        return new ResponseEntity<>(financialTransactionDTO, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransactionById(@Min(1) @NotNull @PathVariable Long id, Principal principal) {
        String email = principal.getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userId = user.get().getId();

        financialTransactionService.deleteTransaction(id, userId);

        return new ResponseEntity<>("Transaction deleted successfully", HttpStatus.OK);

    }


}
