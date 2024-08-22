package com.example.trainingsapp.financialtransaktion.impl;

import com.example.trainingsapp.financialtransaktion.api.FinancialTransactionModelMapper;
import com.example.trainingsapp.financialtransaktion.api.FinancialTransactionRepository;
import com.example.trainingsapp.financialtransaktion.api.FinancialTransactionService;
import com.example.trainingsapp.financialtransaktion.api.dto.FinancialTransactionCreateDTO;
import com.example.trainingsapp.financialtransaktion.api.dto.FinancialTransactionDTO;
import com.example.trainingsapp.financialtransaktion.api.model.FinancialTransaction;
import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryRepository;
import com.example.trainingsapp.financialtransaktioncategory.api.model.FinancialTransactionCategory;
import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.wallet.api.WalletRepository;
import com.example.trainingsapp.wallet.api.model.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class FinancialTransactionServiceImpl implements FinancialTransactionService {
    @Autowired
    private FinancialTransactionRepository financialTransactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    FinancialTransactionCategoryRepository financialTransactionCategoryRepository;

    @Autowired
    FinancialTransactionModelMapper financialTransactionModelMapper;

    @Override
    public FinancialTransactionDTO createFinancialTransaction(
            FinancialTransactionCreateDTO ftCreateDTO, Long userId) {

        Long walletId = ftCreateDTO.getWalletId();
        Wallet wallet = walletRepository.findByIdAndUserId(walletId, userId).orElseThrow(() ->
                new AppRuntimeException(ErrorCode.W001, String.format("Wallet with this id: %d not exist", walletId)));
        FinancialTransactionCategory ftCategory = findFinancialTransactionCategory(ftCreateDTO.getCategoryId(), userId);

        if (ftCreateDTO.getCategoryId() != null && ftCreateDTO.getType() != ftCategory.getType())
            throw new AppRuntimeException(ErrorCode.FT002, String.format(
                    "Financial transaction type: %s does not match financial category type: %s",
                    ftCreateDTO.getType(), ftCategory.getType()));

        FinancialTransaction financialTransaction = buildFinancialTransaction(ftCreateDTO, wallet, ftCategory);
        FinancialTransaction savedFinancialTransaction = financialTransactionRepository.save(financialTransaction);

        return financialTransactionModelMapper.mapFinancialTransactionEntityToFinancialTransactionDTO(
                savedFinancialTransaction);

    }

    private FinancialTransactionCategory findFinancialTransactionCategory(Long categoryId, Long userId) {
        if (categoryId != null) {
            Optional<FinancialTransactionCategory> ftCategory = financialTransactionCategoryRepository
                    .findByIdAndUserId(categoryId, userId);
            if (ftCategory.isEmpty()) {
                throw new AppRuntimeException(ErrorCode.FT001, String.format(
                        "Financial transaction category with this id: %d not exist", categoryId));
            }
            return ftCategory.get();
        }
        return null;

    }

    private FinancialTransaction buildFinancialTransaction(FinancialTransactionCreateDTO ftCreateDTO, Wallet wallet,
                                                           FinancialTransactionCategory ftCategory) {
        return FinancialTransaction.builder()
                .wallet(wallet)
                .type(ftCreateDTO.getType())
                .amount(ftCreateDTO.getAmount())
                .date(ftCreateDTO.getDate())
                .financialTransactionCategory(ftCategory)
                .description(ftCreateDTO.getDescription())
                .build();
    }
}


