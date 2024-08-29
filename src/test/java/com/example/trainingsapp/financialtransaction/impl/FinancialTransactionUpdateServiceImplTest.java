package com.example.trainingsapp.financialtransaction.impl;

import com.example.trainingsapp.financialtransaction.api.FinancialTransactionModelMapper;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionRepository;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionDTO;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionUpdateDTO;
import com.example.trainingsapp.financialtransaction.api.model.FinancialTransaction;
import com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType;
import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryRepository;
import com.example.trainingsapp.financialtransaktioncategory.api.model.FinancialTransactionCategory;
import com.example.trainingsapp.user.api.model.User;
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
    private FinancialTransactionRepository ftRepository;

    @Mock
    private FinancialTransactionCategoryRepository financialTransactionCategoryRepository;

    @Mock
    private FinancialTransactionModelMapper financialTransactionModelMapper;

    @InjectMocks
    private FinancialTransactionServiceImpl financialTransactionService;


    @Test
    @DisplayName("Should return financial transaction with updated parameters and status OK")
    void updateFinancialTransaction_ValidParameters_returnFinancialTransactionDTO() {
        // given
        User user = createUserForTest();
        FinancialTransactionUpdateDTO financialTransactionUpdateDTO = createFinancialTransactionUpdateDTO();

        FinancialTransaction financialTransactionEntity = createFinancialTransactionEntity(ID_1L);
        Long financialTransactionId = financialTransactionEntity.getId();
        when(ftRepository.findByIdAndWalletUserId(financialTransactionId, user.getId())).thenReturn(
                Optional.of(financialTransactionEntity));

        FinancialTransactionCategory financialTransactionCategory = createFinancialTransactionCategory(EXPENSE, user);
        when(financialTransactionCategoryRepository.findByIdAndUserId(any(), any())).thenReturn(
                Optional.ofNullable(financialTransactionCategory));

        FinancialTransactionDTO financialTransactionDTO = new FinancialTransactionDTO(
                ID_1L, TEN, NEW_DESCRIPTION, INCOME, NEW_DATE, NEW_CATEGORY_ID);
        when(financialTransactionModelMapper.mapFinancialTransactionEntityToFinancialTransactionDTO(
                any(FinancialTransaction.class))).thenReturn(financialTransactionDTO);

        // when
        FinancialTransactionDTO result = financialTransactionService.updateFinancialTransaction
                (financialTransactionId, financialTransactionUpdateDTO, user.getId());

        // then
        Assertions.assertAll(
                () -> assertEquals(financialTransactionDTO, result),
                () -> assertEquals(financialTransactionDTO.getId(), result.getId()),
                () -> assertEquals(financialTransactionDTO.getAmount(), result.getAmount()));
        verify(ftRepository, atMostOnce()).save(any(FinancialTransaction.class));
        verify(financialTransactionModelMapper, atMostOnce()).mapFinancialTransactionEntityToFinancialTransactionDTO(
                any(FinancialTransaction.class));
        verify(financialTransactionCategoryRepository, atMostOnce()).findByIdAndUserId(any(), any());

    }

    private FinancialTransaction createFinancialTransactionEntity(Long financialTransactionId) {
        FinancialTransaction financialTransaction = new FinancialTransaction();
        financialTransaction.setId(financialTransactionId);
        financialTransaction.setAmount(ONE);
        financialTransaction.setDescription(DESCRIPTION);
        financialTransaction.setType(EXPENSE);
        financialTransaction.setDate(Instant.now());
        return financialTransaction;
    }


    private FinancialTransactionUpdateDTO createFinancialTransactionUpdateDTO() {
        return new FinancialTransactionUpdateDTO(TEN, NEW_DESCRIPTION, EXPENSE,
                NEW_DATE, NEW_CATEGORY_ID);
    }

    private FinancialTransactionCategory createFinancialTransactionCategory(FinancialTransactionType type, User user) {
        return new FinancialTransactionCategory(ID_1L, "Example Category Name", type, null, DATE_NOW, user);

    }

    private User createUserForTest() {
        return User.builder().id(1L).build();
    }
}
