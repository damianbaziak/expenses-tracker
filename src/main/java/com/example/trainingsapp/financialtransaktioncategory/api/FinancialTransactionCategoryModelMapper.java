package com.example.trainingsapp.financialtransaktioncategory.api;

import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.model.FinancialTransactionCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FinancialTransactionCategoryModelMapper {

    @Mapping(source = "user.id", target = "userId")
    FinancialTransactionCategoryDTO mapFinancialTransactionCategoryEntityToFinancialTransactionCategoryDTO(
            FinancialTransactionCategory financialTransactionCategory);
}
