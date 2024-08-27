package com.example.trainingsapp.financialtransaction.api;

import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionDTO;
import com.example.trainingsapp.financialtransaction.api.model.FinancialTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FinancialTransactionModelMapper {

    @Mapping(source = "financialTransactionCategory.id", target = "categoryId")
    FinancialTransactionDTO mapFinancialTransactionEntityToFinancialTransactionDTO(
            FinancialTransaction financialTransaction);
}
