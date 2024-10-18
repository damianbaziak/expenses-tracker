package com.example.trainingsapp.financialtransaktioncategory.impl;

import com.example.trainingsapp.TestUtils;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionRepository;
import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryModelMapper;
import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryRepository;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryDetailedDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.model.FinancialTransactionCategory;
import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.Optional;

import static com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType.EXPENSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinancialTransactionCategoryFindServiceImplTest {

    private static final Long USER_ID_1L = 1L;
    private static final Long CATEGORY_ID_1L = 1L;
    @Mock
    UserRepository userRepository;
    @Mock
    private FinancialTransactionCategoryRepository financialTransactionCategoryRepository;
    @InjectMocks
    private FinancialTransactionCategoryServiceImpl financialTransactionCategoryService;
    @Mock
    private FinancialTransactionRepository financialTransactionRepository;
    @Mock
    private FinancialTransactionCategoryModelMapper financialTransactionCategoryModelMapper;

    @Test
    @DisplayName("Should returns financial transaction category detailed DTO")
        // given
    void findFinancialTransactionCategoryForUser_transactionCategoryExist_returnsFTCDetailedDTO() {
        FinancialTransactionCategory financialTransactionCategory = TestUtils.createFinancialTransactionCategoryForTest(
                EXPENSE);
        when(financialTransactionCategoryRepository.findByIdAndUserId(CATEGORY_ID_1L, USER_ID_1L))
                .thenReturn(Optional.of(financialTransactionCategory));

        BigInteger numberOfFinancialTransactions = new BigInteger("4");
        when(financialTransactionRepository.countFinancialTransactionsByFinancialTransactionCategoryId(CATEGORY_ID_1L))
                .thenReturn(numberOfFinancialTransactions);

        FinancialTransactionCategoryDTO financialTransactionCategoryDTO =
                TestUtils.createFinancialTransactionCategoryDTOForTest(EXPENSE, USER_ID_1L);
        when(financialTransactionCategoryModelMapper
                .mapFinancialTransactionCategoryEntityToFinancialTransactionCategoryDTO(financialTransactionCategory))
                .thenReturn(financialTransactionCategoryDTO);

        FinancialTransactionCategoryDetailedDTO financialTransactionCategoryDetailedDTO =
                new FinancialTransactionCategoryDetailedDTO(financialTransactionCategoryDTO, numberOfFinancialTransactions);

        // when
        FinancialTransactionCategoryDetailedDTO result =
                financialTransactionCategoryService.findFinancialTransactionCategoryForUser(1L, 1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.financialTransactionCategoryDTO()).isEqualTo(financialTransactionCategoryDTO);
        assertThat(result.financialTransactionCounter()).isEqualTo(numberOfFinancialTransactions);

    }

    @Test
    @DisplayName("Should throws an AppRuntimeException when financial category not found")
        // given
    void findFinancialTransactionCategoryForUser_categoryNotFound_throwsAppRuntimeException() {
        when(financialTransactionCategoryRepository.findByIdAndUserId(CATEGORY_ID_1L, USER_ID_1L))
                .thenReturn(Optional.empty());

        // when and then
        AppRuntimeException result = assertThrows(AppRuntimeException.class, () -> financialTransactionCategoryService
                .findFinancialTransactionCategoryForUser(CATEGORY_ID_1L, USER_ID_1L));

        // then
        assertThat(result).hasMessage(ErrorCode.FTC001.getBusinessMessage());
        assertThat(result.getHttpStatusCode()).isEqualTo(ErrorCode.FTC001.getHttpStatusCode());
        assertThat(result.getStatus()).isEqualTo((ErrorCode.FTC001.getBusinessStatus()));

        // Verification that not unnecessary operation were called
        verify(financialTransactionRepository, times(0))
                .countFinancialTransactionsByFinancialTransactionCategoryId(any());
        verify(financialTransactionCategoryModelMapper, times(0))
                .mapFinancialTransactionCategoryEntityToFinancialTransactionCategoryDTO(
                        any(FinancialTransactionCategory.class));
    }
}

