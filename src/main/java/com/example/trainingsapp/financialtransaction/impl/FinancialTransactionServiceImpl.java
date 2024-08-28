package com.example.trainingsapp.financialtransaction.impl;

import com.example.trainingsapp.financialtransaction.api.FinancialTransactionModelMapper;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionRepository;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionService;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionCreateDTO;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionDTO;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionUpdateDTO;
import com.example.trainingsapp.financialtransaction.api.model.FinancialTransaction;
import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryRepository;
import com.example.trainingsapp.financialtransaktioncategory.api.model.FinancialTransactionCategory;
import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.wallet.api.WalletRepository;
import com.example.trainingsapp.wallet.api.model.Wallet;
import jakarta.transaction.Transactional;
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

    @Override
    @Transactional
    public FinancialTransactionDTO updateFinancialTransaction(Long financialTransactionId,
                                                              FinancialTransactionUpdateDTO financialTransactionUpdateDTO, Long userId) {
        FinancialTransaction financialTransaction = financialTransactionRepository.findById(financialTransactionId)
                .orElseThrow(() -> new AppRuntimeException(ErrorCode.FT001, String.format(
                        "Financial transaction with this id: %d not exist", financialTransactionId)));

        if (financialTransactionUpdateDTO.getCategoryId() != null) {
            FinancialTransactionCategory financialTransactionCategory = findFinancialTransactionCategory(
                    financialTransactionUpdateDTO.getCategoryId(), userId);

            if (financialTransactionUpdateDTO.getType() != financialTransactionCategory.getType()) {
                throw new AppRuntimeException(ErrorCode.FT002, String.format(
                        "Financial transaction type: %s does not match financial category type: %s",
                        financialTransactionUpdateDTO.getType(), financialTransactionCategory.getType()));
            }

            financialTransaction.setFinancialTransactionCategory(financialTransactionCategory);
        }
        financialTransaction.setAmount(financialTransactionUpdateDTO.getAmount());
        financialTransaction.setDescription(financialTransactionUpdateDTO.getDescription());
        financialTransaction.setDate(financialTransactionUpdateDTO.getDate());
        financialTransaction.setType(financialTransactionUpdateDTO.getType());

        FinancialTransaction updatedFinancialTransaction = financialTransactionRepository.save(financialTransaction);

        return financialTransactionModelMapper.mapFinancialTransactionEntityToFinancialTransactionDTO(
                updatedFinancialTransaction);
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


