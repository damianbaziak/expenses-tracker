package com.example.trainingsapp.financialtransaktioncategory;

import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionCategoryDetailedDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryService;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryCreateDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryDTO;
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
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/api/categories")
public class FinancialTransactionCategoryController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FinancialTransactionCategoryService financialTransactionCategoryService;


    @PostMapping()
    public ResponseEntity<FinancialTransactionCategoryDTO> createCategory(
            @Valid @RequestBody FinancialTransactionCategoryCreateDTO categoryCreateDTO, Principal principal) {
        String email = principal.getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userID = user.get().getId();

        FinancialTransactionCategoryDTO categoryDTO =
                financialTransactionCategoryService.createCategory(categoryCreateDTO, userID);

        return new ResponseEntity<>(categoryDTO, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<FinancialTransactionCategoryDetailedDTO> getFinancialCategoryById(
            @NotNull @Min(1) Long id, Principal principal) {
        String email = principal.getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userID = user.get().getId();

        FinancialTransactionCategoryDetailedDTO categoryDetailedDTO = financialTransactionCategoryService
                .findFinancialTransactionCategoryForUser(id, userID);

        return new ResponseEntity<>(categoryDetailedDTO, HttpStatus.OK);

    }
}
