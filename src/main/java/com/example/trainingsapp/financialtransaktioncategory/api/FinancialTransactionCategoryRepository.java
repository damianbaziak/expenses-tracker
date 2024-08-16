package com.example.trainingsapp.financialtransaktioncategory.api;

import com.example.trainingsapp.financialtransaktioncategory.model.FinancialTransactionCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FinancialTransactionCategoryRepository extends CrudRepository<FinancialTransactionCategory, Long> {
    List<FinancialTransactionCategory> findAllByUserIdOrderByName(Long userId);
}
