package com.example.trainingsapp.financialtransaction;

import com.example.trainingsapp.financialtransaction.api.FinancialTransactionService;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionCreateDTO;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionDTO;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.model.User;
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

        FinancialTransactionDTO createdTransaction =
                financialTransactionService.createFinancialTransaction(financialTransactionCreateDTO, userID);

        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }
}
