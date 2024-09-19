package com.example.trainingsapp.financialtransaction.api;

import com.example.trainingsapp.financialtransaction.api.model.FinancialTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FinancialTransactionRepository extends CrudRepository<FinancialTransaction, Long> {
    Optional<FinancialTransaction> findByIdAndWalletUserId(Long financialTransactionId, Long walletId);

    List<FinancialTransaction> findAllByWalletIdAndWalletUserIdOrderByDateDesc(Long walletId, Long userId);

    boolean existsByIdAndWalletUserId(Long id, Long userId);
}
