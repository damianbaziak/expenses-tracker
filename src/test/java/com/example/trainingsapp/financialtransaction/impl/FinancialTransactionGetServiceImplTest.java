package com.example.trainingsapp.financialtransaction.impl;

import com.example.trainingsapp.TestUtils;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionModelMapper;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionRepository;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionDTO;
import com.example.trainingsapp.financialtransaction.api.model.FinancialTransaction;
import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.model.User;
import com.example.trainingsapp.wallet.api.WalletRepository;
import com.example.trainingsapp.wallet.api.model.Wallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType.EXPENSE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FinancialTransactionGetServiceImplTest {
    private static final Long ID_1L = 1L;
    private static final Long WALLET_ID_1L = 1L;

    @Mock
    private FinancialTransactionRepository ftRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private FinancialTransactionModelMapper financialTransactionModelMapper;

    @InjectMocks
    private FinancialTransactionServiceImpl financialTransactionService;

    @Test
    @DisplayName("Should return financial transactions DTOs")
    void getFinancialTransactionsByWalletId_validParameters_returnFinancialTransactionDTOs() {
        // given
        User user = TestUtils.createUserForTest();
        Wallet wallet = TestUtils.createWalletForTest(user);

        List<FinancialTransaction> ftList = TestUtils.createFinancialTransactionListForTest(
                3, wallet, EXPENSE);

        when(walletRepository.findByIdAndUserId(WALLET_ID_1L, user.getId())).thenReturn(Optional.of(wallet));
        when(ftRepository.findAllByWalletIdAndWalletUserIdOrderByDateDesc(WALLET_ID_1L, user.getId())).thenReturn(ftList);
        when(financialTransactionModelMapper.mapFinancialTransactionEntityToFinancialTransactionDTO(
                any(FinancialTransaction.class)))
                .thenAnswer(invocation -> {
                    FinancialTransaction transaction = invocation.getArgument(0);
                    return new FinancialTransactionDTO(transaction.getId(), transaction.getAmount(), transaction.getDescription(),
                            transaction.getType(), transaction.getDate(), ID_1L);
                });

        // when
        List<FinancialTransactionDTO> result = financialTransactionService.getFinancialTransactionsByWalletId(
                WALLET_ID_1L, ID_1L);

        // then
        Assertions.assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals(1L, result.get(0).getId()),
                () -> assertEquals(2L, result.get(1).getId()),
                () -> assertEquals(ftList.get(0).getDescription(), result.get(0).getDescription()),
                () -> assertEquals(ftList.get(1).getDescription(), result.get(1).getDescription()),
                () -> assertEquals(new BigDecimal(101), result.get(0).getAmount()),
                () -> assertEquals(new BigDecimal(103), result.get(2).getAmount())
        );
        verify(walletRepository, atMostOnce()).findByIdAndUserId(any(), any());
    }

        @Test
        @DisplayName("Should throw an AppRuntimeException when wallet not exist")
        void getFinancialTransactionsByWalletId_walletNotExist_throwAppRuntimeException () {
            // given
            User user = TestUtils.createUserForTest();

            when(walletRepository.findByIdAndUserId(2L, user.getId())).thenReturn(Optional.empty());

            // when
            AppRuntimeException result = assertThrows(AppRuntimeException.class,
                    () -> financialTransactionService.getFinancialTransactionsByWalletId(2L, user.getId()));

            // then
            Assertions.assertEquals(ErrorCode.W001.getBusinessMessage(), result.getMessage());
            Assertions.assertEquals(ErrorCode.W001.getBusinessStatusCode(), result.getStatusCode());
            verify(ftRepository, never()).findAllByWalletIdAndWalletUserIdOrderByDateDesc(any(), any());
        }

    @Test
    @DisplayName("Should return an empty List when there are no financial transactions in the wallet")
    void getFinancialTransactionsByWalletId_noFinancialTransactions_returnEmptyList() {
        // given
        User user = TestUtils.createUserForTest();
        Wallet wallet = TestUtils.createWalletForTest(user);

        when(walletRepository.findByIdAndUserId(WALLET_ID_1L, user.getId())).thenReturn(Optional.of(wallet));
        when(ftRepository.findAllByWalletIdAndWalletUserIdOrderByDateDesc(WALLET_ID_1L, user.getId())).thenReturn(
                Collections.emptyList());

        // when
        List<FinancialTransactionDTO> result = financialTransactionService.getFinancialTransactionsByWalletId(
                WALLET_ID_1L, ID_1L);

        // then
        Assertions.assertTrue(result.isEmpty());
        verify(walletRepository, atMostOnce()).findByIdAndUserId(any(), any());
    }





}