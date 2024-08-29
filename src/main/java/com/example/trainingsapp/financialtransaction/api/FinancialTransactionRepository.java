package com.example.trainingsapp.financialtransaction.api;

import com.example.trainingsapp.financialtransaction.api.model.FinancialTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FinancialTransactionRepository extends CrudRepository<FinancialTransaction, Long> {
    Optional<FinancialTransaction> findByIdAndWalletUserId(Long financialTransactionId, Long walletId);
}
