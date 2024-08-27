package com.example.trainingsapp.financialtransaction.api;

import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionCreateDTO;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionDTO;

public interface FinancialTransactionService {
    FinancialTransactionDTO createFinancialTransaction(
            FinancialTransactionCreateDTO financialTransactionCreateDTO, Long userId);
}
