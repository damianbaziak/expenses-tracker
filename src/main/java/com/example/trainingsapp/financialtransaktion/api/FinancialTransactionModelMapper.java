package com.example.trainingsapp.financialtransaktion.api;

import com.example.trainingsapp.financialtransaktion.api.dto.FinancialTransactionDTO;
import com.example.trainingsapp.financialtransaktion.api.model.FinancialTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FinancialTransactionModelMapper {

    @Mapping(source = "financialTransactionCategory.id", target = "categoryId")
    FinancialTransactionDTO mapFinancialTransactionEntityToFinancialTransactionDTO(
            FinancialTransaction financialTransaction);
}
