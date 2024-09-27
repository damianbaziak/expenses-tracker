package com.example.trainingsapp.financialtransaktioncategory;

import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryService;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryCreateDTO;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<FinancialTransactionCategoryCreateDTO> createCategory(
            @Valid @RequestBody FinancialTransactionCategoryCreateDTO categoryCreateDTO, Principal principal) {
        String email = principal.getName();
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long userID = user.get().getId();

        financialTransactionCategoryService.createCategory(categoryCreateDTO, userID);

        return new ResponseEntity<>(categoryCreateDTO, HttpStatus.CREATED);




    }
}
