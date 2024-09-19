package com.example.trainingsapp;

import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionDTO;
import com.example.trainingsapp.financialtransaction.api.model.FinancialTransaction;
import com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType;
import com.example.trainingsapp.financialtransaktioncategory.api.model.FinancialTransactionCategory;
import com.example.trainingsapp.user.api.model.User;
import com.example.trainingsapp.wallet.api.model.Wallet;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ONE;

public class TestUtils {

    private static final Long USER_ID_1L = 1L;
    private static final String USER_EMAIL = "user.example@email.com";
    private static final Long WALLET_ID_1L = 1L;
    private static final String EXAMPLE_DESCRIPTION = "Example description_";
    private static final Long ID_1L = 1L;
    private static final Long CATEGORY_ID = 1L;


    public static User createUserForTest() {
        return User.builder()
                .id(USER_ID_1L)
                .email(USER_EMAIL)
                .build();
    }

    public static User createUserForTest(String email) {
        return User.builder()
                .id(USER_ID_1L)
                .email(email)
                .build();
    }

    public static Wallet createWalletForTest(User user) {
        return Wallet.builder()
                .id(WALLET_ID_1L)
                .user(user)
                .name("Example wallet name")
                .creationDate(Instant.now())
                .build();

    }

    public static FinancialTransaction createFinancialTransactionForTest(FinancialTransactionType type,
                                                                         FinancialTransactionCategory category) {
        return FinancialTransaction.builder()
                .id(ID_1L)
                .amount(ONE)
                .description(EXAMPLE_DESCRIPTION)
                .type(type)
                .date(Instant.now())
                .financialTransactionCategory(category)
                .build();
    }

    public static FinancialTransactionDTO createFinancialTransactionDTOForTest(FinancialTransactionType type) {
        return new FinancialTransactionDTO(ID_1L, ONE, EXAMPLE_DESCRIPTION, type, Instant.now(), CATEGORY_ID);
    }

    public static List<FinancialTransaction> createFinancialTransactionListForTest(
            int count, Wallet wallet, FinancialTransactionType type) {
        BigDecimal amount = new BigDecimal(100);
        ArrayList<FinancialTransaction> list = new ArrayList<>();
        for (long i = 1; i <= count; i++) {
            amount = amount.add(BigDecimal.ONE);
            list.add(FinancialTransaction.builder()
                    .id(i)
                    .wallet(wallet)
                    .type(type)
                    .amount(amount)
                    .date(Instant.now())
                    .description(EXAMPLE_DESCRIPTION + i)
                    .build()
            );
        }
        return list;

    }

    public static List<FinancialTransactionDTO> createFinancialTransactionDTOListForTest(
            int count, FinancialTransactionType type, Long categoryId) {
        BigDecimal amount = new BigDecimal(100);
        ArrayList<FinancialTransactionDTO> list = new ArrayList<>();
        for (long i = 1; i <= count; i++) {
            amount = amount.add(BigDecimal.ONE);
            list.add(new FinancialTransactionDTO(
                    i,
                    amount,
                    EXAMPLE_DESCRIPTION + i,
                    type,
                    Instant.now(),
                    categoryId)
            );
        }
        return list;

    }

    public static FinancialTransactionCategory createFinancialTransactionCategoryForTest(
            String name, FinancialTransactionType type, User user) {
        return FinancialTransactionCategory.builder()
                .id(ID_1L)
                .name(name)
                .type(type)
                .creationDate(Instant.now())
                .user(user)
                .build();
    }
}
