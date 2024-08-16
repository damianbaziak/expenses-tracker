package com.example.trainingsapp.financialtransaktion.impl;

import com.example.trainingsapp.financialtransaktion.api.FinancialTransactionRepository;
import com.example.trainingsapp.financialtransaktion.api.FinancialTransactionService;
import com.example.trainingsapp.financialtransaktion.api.dto.FinancialTransactionCreateDTO;
import com.example.trainingsapp.financialtransaktion.api.dto.FinancialTransactionDTO;
import com.example.trainingsapp.financialtransaktion.model.FinancialTransaction;
import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.wallet.api.WalletRepository;
import com.example.trainingsapp.wallet.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;

public class FinancialTransactionServiceImpl implements FinancialTransactionService {
    @Autowired
    private FinancialTransactionRepository financialTransactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public FinancialTransactionDTO createFinancialTransaction(
            FinancialTransactionCreateDTO financialTransactionCreateDTO, Long userId) {

        Long walletId = financialTransactionCreateDTO.getWalletId();
        Wallet wallet = walletRepository.findByIdAndUserId(walletId, userId).orElseThrow(() ->
                new AppRuntimeException(ErrorCode.W001, String.format("Wallet with this id: %d not exist", walletId)));

        FinancialTransaction financialTransaction = new FinancialTransaction(userId, wallet, financialTransactionCreateDTO.getType(),
                financialTransactionCreateDTO.getDate(), 

    }
}
