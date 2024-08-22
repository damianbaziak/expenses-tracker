package com.example.trainingsapp.financialtransaktioncategory.api;

import com.example.trainingsapp.financialtransaktioncategory.api.model.FinancialTransactionCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FinancialTransactionCategoryRepository extends CrudRepository<FinancialTransactionCategory, Long> {
    List<FinancialTransactionCategory> findAllByUserIdOrderByName(Long userId);
    Optional<FinancialTransactionCategory> findByIdAndUserId(Long id, Long userId);
}
