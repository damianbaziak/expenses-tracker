package com.example.trainingsapp.financialtransaction.impl;


import com.example.trainingsapp.TestUtils;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionRepository;
import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinancialTransactionDeleteServiceImplTest {
    private static final Long TRANSACTION_ID_1L = 1L;

    @Mock
    private FinancialTransactionRepository financialTransactionRepository;

    @InjectMocks
    private FinancialTransactionServiceImpl financialTransactionService;

    @Test
    @DisplayName("Should call the delete method once")
    void deleteTransaction_transactionExist() {
        // given
        User user = TestUtils.createUserForTest();
        when(financialTransactionRepository.existsByIdAndWalletUserId(
                TRANSACTION_ID_1L, user.getId())).thenReturn(Boolean.TRUE);

        // when
        financialTransactionService.deleteTransaction(TRANSACTION_ID_1L, user.getId());

        // then
        verify(financialTransactionRepository, times(1)).existsByIdAndWalletUserId(
                TRANSACTION_ID_1L, user.getId());
        verify(financialTransactionRepository, times(1)).deleteById(1L);


    }

    @Test
    @DisplayName("Should return an AppRuntimeException")
    void deleteTransaction_transactionNotExist() {
        // given
        User user = TestUtils.createUserForTest();
        when(financialTransactionRepository.existsByIdAndWalletUserId(
                TRANSACTION_ID_1L, user.getId())).thenReturn(Boolean.FALSE);

        // when and then
        AppRuntimeException result = Assertions.assertThrows(AppRuntimeException.class,
                () -> financialTransactionService.deleteTransaction(TRANSACTION_ID_1L, user.getId()));

        Assertions.assertEquals(ErrorCode.FT001.getBusinessMessage(), result.getMessage());
        Assertions.assertEquals(ErrorCode.FT001.getHttpStatusCode(), result.getHttpStatusCode());
        verify(financialTransactionRepository, times(1)).existsByIdAndWalletUserId(
                TRANSACTION_ID_1L, user.getId());
        verify(financialTransactionRepository, never()).deleteById(any());
    }
}

