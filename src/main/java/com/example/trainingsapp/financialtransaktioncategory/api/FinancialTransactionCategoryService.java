package com.example.trainingsapp.financialtransaktioncategory.api;

import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryDetailedDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryCreateDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryDTO;

import java.security.Principal;
import java.util.List;

public interface FinancialTransactionCategoryService {
    FinancialTransactionCategoryDTO createCategory(
            FinancialTransactionCategoryCreateDTO categoryCreateDTO, Long userID);

    FinancialTransactionCategoryDetailedDTO findFinancialTransactionCategoryForUser(Long id, Long userID);

    List<FinancialTransactionCategoryDTO> findFinancialTransactionCategories(Long userId);
}
