package com.example.trainingsapp.financialtransaction;

import com.example.trainingsapp.TestUtils;
import com.example.trainingsapp.authorization.JwtAuthorizationFilter;
import com.example.trainingsapp.authorization.api.MyUserDetailsService;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionRepository;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionService;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionDTO;
import com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType;
import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.model.User;
import com.example.trainingsapp.wallet.api.model.Wallet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType.EXPENSE;
import static com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType.INCOME;
import static java.math.BigInteger.ONE;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FinancialTransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class FinancialTransactionGetControllerTest {
    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private static final Long ID_3 = 3L;
    private static final BigDecimal AMOUNT_101 = new BigDecimal(101);
    private static final BigDecimal AMOUNT_102 = new BigDecimal(102);
    private static final BigDecimal AMOUNT_103 = new BigDecimal(103);
    private static final String EXAMPLE_DESCRIPTION_1 = "Example description_1";
    private static final String EXAMPLE_DESCRIPTION_2 = "Example description_2";
    private static final String EXAMPLE_DESCRIPTION_3 = "Example description_3";

    private static final Long CATEGORY_ID_1L = 1L;
    private static final Long WALLET_ID_1L = 1L;
    private static final Long USER_ID_1L = 1L;
    private static final String USER_EMAIL = "example@email.com";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialTransactionService financialTransactionService;

    @MockBean
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @MockBean
    private MyUserDetailsService myUserDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Should return status OK and all financial transactions list")
    void getFinancialTransactionsByWalletId_transactionsExist_shouldReturnFinancialTransactionsList() throws Exception {
        // given
        User user = TestUtils.createUserForTest();
        List<FinancialTransactionDTO> financialTransactionDTOS = TestUtils.createFinancialTransactionDTOListForTest(
                3, EXPENSE, CATEGORY_ID_1L);

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));
        when(financialTransactionService.getFinancialTransactionsByWalletId(WALLET_ID_1L, USER_ID_1L))
                .thenReturn(financialTransactionDTOS);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/transactions").param(
                        "walletId", String.valueOf(WALLET_ID_1L))
                .principal(() -> USER_EMAIL));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(financialTransactionDTOS.size()))
                .andExpect(jsonPath("$[0].id").value(String.valueOf(ID_1)))
                .andExpect(jsonPath("$[1].id").value(String.valueOf(ID_2)))
                .andExpect(jsonPath("$[2].id").value(String.valueOf(ID_3)))
                .andExpect(jsonPath("$[0].amount").value(AMOUNT_101))
                .andExpect(jsonPath("$[1].amount").value(AMOUNT_102))
                .andExpect(jsonPath("$[2].amount").value(AMOUNT_103))
                .andExpect(jsonPath("$[0].description").value(EXAMPLE_DESCRIPTION_1))
                .andExpect(jsonPath("$[1].description").value(EXAMPLE_DESCRIPTION_2))
                .andExpect(jsonPath("$[2].description").value(EXAMPLE_DESCRIPTION_3));


    }

    @Test
    @DisplayName("Should return an empty list and status OK when there are no financial transactions in the wallet")
    void getFinancialTransactionsByWalletId_noFinancialTransactionsExist_shouldReturnEmptyList() throws Exception {
        // given
        User user = TestUtils.createUserForTest();

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));
        when(financialTransactionService.getFinancialTransactionsByWalletId(WALLET_ID_1L, USER_ID_1L))
                .thenReturn(Collections.emptyList());

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/transactions").param(
                        "walletId", String.valueOf(WALLET_ID_1L))
                .principal(() -> USER_EMAIL));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));


    }

    @Test
    @DisplayName("Should return financial transaction and status OK")
    void getTransactionById_transactionExist_shouldReturnFinancialTransaction() throws Exception {
        // given
        User user = TestUtils.createUserForTest();
        FinancialTransactionDTO financialTransactionDTO = TestUtils.createFinancialTransactionDTOForTest(INCOME);
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));
        when(financialTransactionService.findFinancialTransactionForUser(ID_1, USER_ID_1L)).thenReturn(
                financialTransactionDTO);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/transactions/{id}", ID_1)
                .principal(() -> USER_EMAIL));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_1))
                .andExpect(jsonPath("$.amount").value(ONE))
                .andExpect(jsonPath("$.type").value(String.valueOf(INCOME)))
                .andExpect(jsonPath("$.categoryId").value(CATEGORY_ID_1L));

    }

    @Test
    @DisplayName("Should return status 404 - not found when financial transaction ID not exist")
    void getTransactionById_transactionNotExist_shouldReturnFinancialTransaction() throws Exception {
        // given
        User user = TestUtils.createUserForTest();
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));
        doThrow(new AppRuntimeException(ErrorCode.FT001, "")).when(
                financialTransactionService).findFinancialTransactionForUser(ID_1, USER_ID_1L);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/transactions/{id}", ID_1)
                .principal(() -> USER_EMAIL));

        // then
        resultActions.andExpect(status().isNotFound());

    }


}

