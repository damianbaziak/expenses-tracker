package com.example.trainingsapp.financialtransaktion.api;

import com.example.trainingsapp.financialtransaktion.api.dto.FinancialTransactionCreateDTO;
import com.example.trainingsapp.financialtransaktion.api.dto.FinancialTransactionDTO;

public interface FinancialTransactionService {
    FinancialTransactionDTO createFinancialTransaction(
            FinancialTransactionCreateDTO financialTransactionCreateDTO, Long userId);
}
