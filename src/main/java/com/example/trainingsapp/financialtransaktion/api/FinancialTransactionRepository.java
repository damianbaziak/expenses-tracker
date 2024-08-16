package com.example.trainingsapp.financialtransaktion.api;

import com.example.trainingsapp.financialtransaktion.model.FinancialTransaction;
import org.springframework.data.repository.CrudRepository;

public interface FinancialTransactionRepository extends CrudRepository<FinancialTransaction, Long> {
}
