package com.example.trainingsapp.financialtransaktioncategory.impl;

import com.example.trainingsapp.financialtransaction.api.FinancialTransactionRepository;
import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryModelMapper;
import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryRepository;
import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryService;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryCreateDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryDetailedDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryUpdateDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.model.FinancialTransactionCategory;
import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class FinancialTransactionCategoryServiceImpl implements FinancialTransactionCategoryService {
    private final String CATEGORY_WITH_ID_NOT_FOUND_FOR_USER =
            "Financial transaction category with id: %d not found for user";

    @Autowired
    private FinancialTransactionCategoryRepository financialTransactionCategoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FinancialTransactionCategoryModelMapper financialCategoryModelMapper;
    @Autowired
    private FinancialTransactionRepository financialTransactionRepository;

    @Override
    public FinancialTransactionCategoryDTO createCategory(
            FinancialTransactionCategoryCreateDTO categoryCreateDTO, Long userID) {
        User userForCategory = getUserByUserId(userID);

        FinancialTransactionCategory financialTransactionCategory = FinancialTransactionCategory
                .builder()
                .name(categoryCreateDTO.getName())
                .type(categoryCreateDTO.getType())
                .user(userForCategory)
                .build();
        FinancialTransactionCategory transactionCategory =
                financialTransactionCategoryRepository.save(financialTransactionCategory);

        return financialCategoryModelMapper.mapFinancialTransactionCategoryEntityToFinancialTransactionCategoryDTO(
                transactionCategory);

    }

    @Override
    public FinancialTransactionCategoryDetailedDTO findFinancialTransactionCategoryForUser(Long categoryId, Long userID) {
        FinancialTransactionCategory financialTransactionCategory = financialTransactionCategoryRepository
                .findByIdAndUserId(categoryId, userID).orElseThrow(() -> new AppRuntimeException(ErrorCode.FTC001,
                        String.format(CATEGORY_WITH_ID_NOT_FOUND_FOR_USER, categoryId)));

        BigInteger numberOfFinancialTransactions = financialTransactionRepository
                .countFinancialTransactionsByFinancialTransactionCategoryId(categoryId);

        FinancialTransactionCategoryDTO financialTransactionCategoryDTO = financialCategoryModelMapper
                .mapFinancialTransactionCategoryEntityToFinancialTransactionCategoryDTO(financialTransactionCategory);

        return new FinancialTransactionCategoryDetailedDTO(
                financialTransactionCategoryDTO, numberOfFinancialTransactions);
    }

    @Override
    public List<FinancialTransactionCategoryDTO> findFinancialTransactionCategories(Long userId) {
        List<FinancialTransactionCategory> financialTransactionCategoryList =
                financialTransactionCategoryRepository.findAllByUserId(userId);

        return financialTransactionCategoryList.stream()
                .map(financialTransactionCategory -> new FinancialTransactionCategoryDTO(
                        financialTransactionCategory.getId(), financialTransactionCategory.getName(),
                        financialTransactionCategory.getType(), financialTransactionCategory.getCreationDate(),
                        financialTransactionCategory.getUser().getId()))
                .toList();
    }

    @Override
    @Transactional
    public FinancialTransactionCategoryDTO updateFinancialTransactionCategory(
            Long categoryId, FinancialTransactionCategoryUpdateDTO categoryUpdateDTO, Long userId) {

        FinancialTransactionCategory financialTransactionCategory =
                financialTransactionCategoryRepository.findByIdAndUserId(categoryId, userId).orElseThrow(
                        () -> new AppRuntimeException(ErrorCode.FTC001, CATEGORY_WITH_ID_NOT_FOUND_FOR_USER));

        User user = getUserByUserId(userId);

        financialTransactionCategory.setName(categoryUpdateDTO.getName());
        financialTransactionCategory.setType(categoryUpdateDTO.getType());
        financialTransactionCategory.setUser(user);

        return financialCategoryModelMapper.mapFinancialTransactionCategoryEntityToFinancialTransactionCategoryDTO(
                financialTransactionCategory
        );


    }

    @Override
    public void deleteCategory(Long categoryId, Long userId) {
        if (financialTransactionCategoryRepository.existsByIdAndUserId(categoryId, userId)) {
            financialTransactionCategoryRepository.deleteById(categoryId);
        } else {
            throw new AppRuntimeException(ErrorCode.FTC001,
                    String.format(CATEGORY_WITH_ID_NOT_FOUND_FOR_USER, categoryId));
        }
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new AppRuntimeException(ErrorCode.U003, String.format("User with id: %d doesn't exist.", userId)));
    }

}
