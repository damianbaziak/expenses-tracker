package com.example.trainingsapp.financialtransaktioncategory.api;

import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryCreateDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryDTO;

public interface FinancialTransactionCategoryService {
    FinancialTransactionCategoryDTO createCategory(
            FinancialTransactionCategoryCreateDTO categoryCreateDTO, Long userID);
}
