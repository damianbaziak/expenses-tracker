package com.example.trainingsapp.financialtransaktioncategory.impl;

import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryModelMapper;
import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryRepository;
import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryService;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryCreateDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.model.FinancialTransactionCategory;
import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class FinancialTransactionCategoryServiceImpl implements FinancialTransactionCategoryService {

    @Autowired
    FinancialTransactionCategoryRepository financialTransactionCategoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FinancialTransactionCategoryModelMapper modelMapper;

    @Override
    public FinancialTransactionCategoryDTO createCategory(
            FinancialTransactionCategoryCreateDTO categoryCreateDTO, Long userID) {
        User userForCategory = getUserByUserId(userID);

        FinancialTransactionCategory financialTransactionCategory = FinancialTransactionCategory
                .builder()
                .name(categoryCreateDTO.getName())
                .type(categoryCreateDTO.getType())
                .creationDate(Instant.now())
                .user(userForCategory)
                .build();
        FinancialTransactionCategory transactionCategory =
                financialTransactionCategoryRepository.save(financialTransactionCategory);

        return modelMapper.mapFinancialTransactionCategoryEntityToFinancialTransactionCategoryDTO(
                transactionCategory);

    }

    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new AppRuntimeException(ErrorCode.U003, String.format("User with id: %d doesn't exist.", userId)));
    }

}
