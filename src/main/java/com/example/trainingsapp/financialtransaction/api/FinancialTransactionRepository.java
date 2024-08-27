package com.example.trainingsapp.financialtransaction.api;

import com.example.trainingsapp.financialtransaction.api.model.FinancialTransaction;
import org.springframework.data.repository.CrudRepository;

public interface FinancialTransactionRepository extends CrudRepository<FinancialTransaction, Long> {
}
