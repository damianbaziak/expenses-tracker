package com.example.trainingsapp.financialtransaction.api;

import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionCreateDTO;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionDTO;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionUpdateDTO;

import java.util.List;

public interface FinancialTransactionService {
    FinancialTransactionDTO createFinancialTransaction(
            FinancialTransactionCreateDTO financialTransactionCreateDTO, Long userId);

    FinancialTransactionDTO updateFinancialTransaction(Long financialTransactionId,
            FinancialTransactionUpdateDTO financialTransactionUpdateDTO, Long userId);

    List<FinancialTransactionDTO> getFinancialTransactionsByWalletId(Long walletId, Long userId);
    FinancialTransactionDTO findFinancialTransactionForUser(Long id, Long userId);
}
