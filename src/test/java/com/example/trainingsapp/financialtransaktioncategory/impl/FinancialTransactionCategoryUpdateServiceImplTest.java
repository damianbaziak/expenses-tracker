package com.example.trainingsapp.financialtransaktioncategory.impl;

import com.example.trainingsapp.TestUtils;
import com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType;
import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryModelMapper;
import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryRepository;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryCreateDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryUpdateDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.model.FinancialTransactionCategory;
import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType.EXPENSE;
import static com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType.INCOME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinancialTransactionCategoryUpdateServiceImplTest {
    private static final Long USER_ID_1L = 1L;
    private static final Long CATEGORY_ID_1L = 1L;
    private static final String EXAMPLE_CATEGORY_NAME = "Example category name_";
    private static final String NEW_EXAMPLE_CATEGORY_NAME = "Example category name_";
    private static final FinancialTransactionType NEW_TRANSACTION_TYPE = INCOME;

    @Mock
    private FinancialTransactionCategoryRepository financialTransactionCategoryRepository;
    @Mock
    private FinancialTransactionCategoryModelMapper financialTransactionCategoryModelMapper;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private FinancialTransactionCategoryServiceImpl financialTransactionCategoryService;

    @Test
    @DisplayName("Should update financial transaction category and returns updated DTO")
    void updateFinancialTransactionCategory_forValidParameters_shouldReturnsFTC() {
        // given
        FinancialTransactionCategoryUpdateDTO categoryUpdateDTO =
                new FinancialTransactionCategoryUpdateDTO(NEW_EXAMPLE_CATEGORY_NAME, NEW_TRANSACTION_TYPE);

        FinancialTransactionCategory existingCategory = TestUtils.createFinancialTransactionCategoryForTest(EXPENSE);

        FinancialTransactionCategoryDTO expectedCategoryDTO = new FinancialTransactionCategoryDTO();
        expectedCategoryDTO.setName(NEW_EXAMPLE_CATEGORY_NAME);
        expectedCategoryDTO.setType(NEW_TRANSACTION_TYPE);


        when(financialTransactionCategoryRepository.findByIdAndUserId(CATEGORY_ID_1L, USER_ID_1L)).thenReturn(
                Optional.ofNullable(existingCategory));

        when(userRepository.findById(USER_ID_1L)).thenReturn(Optional.of(new User()));

        when(financialTransactionCategoryModelMapper
                .mapFinancialTransactionCategoryEntityToFinancialTransactionCategoryDTO(existingCategory))
                .thenReturn(expectedCategoryDTO);


        // when
        FinancialTransactionCategoryDTO result = financialTransactionCategoryService.updateFinancialTransactionCategory(
                CATEGORY_ID_1L, categoryUpdateDTO, USER_ID_1L);

        // then
        Assertions.assertAll(
                () -> assertEquals(expectedCategoryDTO, result),
                () -> assertEquals(categoryUpdateDTO.getName(), existingCategory.getName()),
                () -> assertEquals(categoryUpdateDTO.getType(), existingCategory.getType()));
        verify(userRepository, times(1)).findById(USER_ID_1L);
        verify(financialTransactionCategoryModelMapper, times(1))
                .mapFinancialTransactionCategoryEntityToFinancialTransactionCategoryDTO(
                        any(FinancialTransactionCategory.class));

    }

    @Test
    @DisplayName("Should returns an AppRuntimeException")
    void updateFinancialTransactionCategory_categoryNotFound_shouldReturnsAnException() {
        // given
        FinancialTransactionCategoryUpdateDTO categoryUpdateDTO =
                new FinancialTransactionCategoryUpdateDTO(NEW_EXAMPLE_CATEGORY_NAME, NEW_TRANSACTION_TYPE);

        when(financialTransactionCategoryRepository.findByIdAndUserId(CATEGORY_ID_1L, USER_ID_1L))
                .thenReturn(Optional.empty());
        // when and then
        AppRuntimeException result = Assertions.assertThrows(AppRuntimeException.class, () ->
                financialTransactionCategoryService.updateFinancialTransactionCategory(
                        CATEGORY_ID_1L, categoryUpdateDTO, USER_ID_1L));

        Assertions.assertAll(
                () -> assertEquals(ErrorCode.FTC001.getHttpStatusCode(), result.getHttpStatusCode()),
                () -> assertEquals(ErrorCode.FTC001.getBusinessMessage(), result.getMessage()));
        verify(financialTransactionCategoryRepository, never()).findById(USER_ID_1L);
        verify(financialTransactionCategoryModelMapper, never())
                .mapFinancialTransactionCategoryEntityToFinancialTransactionCategoryDTO(
                        any(FinancialTransactionCategory.class));
    }


}