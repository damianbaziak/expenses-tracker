package com.example.trainingsapp.financialtransaction.api.dto;

import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryDTO;

import java.math.BigInteger;

public record FinancialTransactionCategoryDetailedDTO(
        FinancialTransactionCategoryDTO financialTransactionCategoryDTO, BigInteger financialTransactionCounter) {
}
