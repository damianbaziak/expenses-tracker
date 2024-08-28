package com.example.trainingsapp.financialtransaction.api;

import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionCreateDTO;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionDTO;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionUpdateDTO;

public interface FinancialTransactionService {
    FinancialTransactionDTO createFinancialTransaction(
            FinancialTransactionCreateDTO financialTransactionCreateDTO, Long userId);

    FinancialTransactionDTO updateFinancialTransaction(Long financialTransactionId,
            FinancialTransactionUpdateDTO financialTransactionUpdateDTO, Long userId);
}
