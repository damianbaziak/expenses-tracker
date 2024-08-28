package com.example.trainingsapp.financialtransaction.impl;

import com.example.trainingsapp.financialtransaction.api.FinancialTransactionModelMapper;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionRepository;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionCreateDTO;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionDTO;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionUpdateDTO;
import com.example.trainingsapp.financialtransaction.api.model.FinancialTransaction;
import com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType;
import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryRepository;
import com.example.trainingsapp.financialtransaktioncategory.api.model.FinancialTransactionCategory;
import com.example.trainingsapp.user.api.model.User;
import com.example.trainingsapp.wallet.api.model.Wallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Instant;
import java.util.Optional;

import static com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType.EXPENSE;
import static com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType.INCOME;
import static java.math.BigDecimal.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FinancialTransactionUpdateServiceImplTest {
    private static final Long ID_1L = 1L;
    private static final String DESCRIPTION = "Description";
    private static final String NEW_DESCRIPTION = "Updated description";
    private static final Instant DATE_NOW = Instant.now();
    private static final Instant NEW_DATE = Instant.parse("2030-12-22T14:30:00.500Z");
    private static final Long CATEGORY_ID = 1L;
    private static final Long NEW_CATEGORY_ID = 2L;

    @Mock
    FinancialTransactionRepository ftRepository;

    @Mock
    FinancialTransactionCategoryRepository financialTransactionCategoryRepository;

    @Mock
    FinancialTransactionModelMapper financialTransactionModelMapper;

    @InjectMocks
    FinancialTransactionServiceImpl financialTransactionService;


    @Test
    @DisplayName("Should return financial transaction DTO with updated parameters and status OK")
    void updateFinancialTransaction_ValidParameters_returnFinancialTransactionDTO() {
        // given
        User user = createUserForTest();

        FinancialTransactionUpdateDTO financialTransactionUpdateDTO = createFinancialTransactionUpdateDTO();

        FinancialTransaction financialTransactionEntity = createFinancialTransactionEntity(ID_1L);
        when(ftRepository.save(Mockito.any(FinancialTransaction.class))).thenReturn(financialTransactionEntity);

        FinancialTransactionDTO financialTransactionDTO = createFinancialTransactionDTO();
        when(financialTransactionModelMapper.mapFinancialTransactionEntityToFinancialTransactionDTO(
                any(FinancialTransaction.class))).thenReturn(financialTransactionDTO);

        FinancialTransactionCategory financialTransactionCategory = createFinancialTransactionCategory(EXPENSE, user);
        when(financialTransactionCategoryRepository.findByIdAndUserId(any(), any())).thenReturn(
                Optional.ofNullable(financialTransactionCategory));

        // when
        FinancialTransactionDTO result = financialTransactionService.createFinancialTransaction(
                financialTransactionCreateDTO, user.getId());

        // then
        Assertions.assertAll(() -> assertEquals(financialTransactionDTO, result), () -> assertEquals(
                financialTransactionDTO.getId(), result.getId()));
        verify(fTrepository, atMostOnce()).save(any(FinancialTransaction.class));
        verify(walletRepository, atMostOnce()).findByIdAndUserId(any(), any());
        verify(financialTransactionModelMapper, atMostOnce()).mapFinancialTransactionEntityToFinancialTransactionDTO(
                any(FinancialTransaction.class));
        verify(financialTransactionCategoryRepository, atMostOnce()).findByIdAndUserId(any(), any());

    }

    public FinancialTransaction createFinancialTransactionEntity(Long financialTransactionId) {
        FinancialTransaction financialTransaction = new FinancialTransaction();
        financialTransaction.setId(financialTransactionId);
        financialTransaction.setType(INCOME);
        financialTransaction.setAmount(TWO);
        financialTransaction.setDate(Instant.now());
        return financialTransaction;
    }


    public FinancialTransactionUpdateDTO createFinancialTransactionUpdateDTO() {
        return new FinancialTransactionUpdateDTO(TEN, NEW_DESCRIPTION, INCOME,
                NEW_DATE, NEW_CATEGORY_ID);
    }

    public FinancialTransactionDTO createFinancialTransactionDTO() {
        return new FinancialTransactionDTO(ID_1L, ONE, DESCRIPTION, EXPENSE, DATE_NOW, CATEGORY_ID);
    }

    public FinancialTransactionCategory createFinancialTransactionCategory(FinancialTransactionType type, User user) {
        return new FinancialTransactionCategory(ID_1L, "Example Category Name", type, null, DATE_NOW, user);

    }

    public User createUserForTest() {
        return User.builder().id(1L).build();
    }
}
