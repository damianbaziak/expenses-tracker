package com.example.trainingsapp.financialtransaction;

import com.example.trainingsapp.TestUtils;
import com.example.trainingsapp.authorization.JwtAuthorizationFilter;
import com.example.trainingsapp.authorization.api.MyUserDetailsService;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionService;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionCreateDTO;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionDTO;
import com.example.trainingsapp.general.exception.ErrorStrategy;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import static com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType.EXPENSE;
import static java.math.BigDecimal.ONE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FinancialTransactionController.class, includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = {ErrorStrategy.class}))
@AutoConfigureMockMvc(addFilters = false)
class FinancialTransactionCreateControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FinancialTransactionService financialTransactionService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JwtAuthorizationFilter jwtAuthorizationFilter;
    @MockBean
    private MyUserDetailsService myUserDetailsService;

    private static final Long ID_1L = 1L;
    private static final Long USER_ID_1L = 1L;
    private static final String USER_EMAIL = "example@email.com";
    private static final String DESCRIPTION = "Example description";
    private static final Long CATEGORY_ID = 1L;
    private static final Instant DATE = Instant.parse("2024-12-22T14:30:00.500Z");
    private static final BigDecimal negativeAmount = BigDecimal.valueOf(-100.00);
    private static final BigDecimal invalidAmountFormat = BigDecimal.valueOf(98.3974932);
    private static final Long WALLET_ID_1 = 1L;


    @Test
    @DisplayName("Should return financial transaction and status 201-Created")
    void createFinancialTransaction_validData_shouldReturnFinancialTransactionAndStatusCreated() throws Exception {
        // given
        User user = TestUtils.createUserForTest();
        FinancialTransactionCreateDTO financialTransactionCreateDTO = createFinancialTransactionCreateDTO();
        FinancialTransactionDTO financialTransactionDTO = createFinancialTransactionDTO();

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));

        when(financialTransactionService.createFinancialTransaction(financialTransactionCreateDTO, USER_ID_1L))
                .thenReturn(financialTransactionDTO);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(financialTransactionCreateDTO)))
                .characterEncoding("UTF-8")
                .principal(() -> USER_EMAIL));

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", CoreMatchers.is(ID_1L.intValue())))
                .andExpect(jsonPath("$.description", CoreMatchers.is(DESCRIPTION)))
                .andExpect(jsonPath("$.type", CoreMatchers.is(EXPENSE.name())));
    }

    @Test
    @DisplayName("Should return bad request status when financialTransactionType is null")
    void createFinancialTransaction_FinancialTransactionTypeNull_shouldReturnStatusBadRequest() throws Exception {
        // given
        FinancialTransactionCreateDTO financialTransactionCreateDTO = createFinancialTransactionCreateDTO();
        financialTransactionCreateDTO.setType(null);
        User user = TestUtils.createUserForTest();

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(financialTransactionCreateDTO)))
                .characterEncoding("UTF-8")
                .principal(() -> USER_EMAIL));

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request status when amount is negative")
    void createFinancialTransaction_negativeAmount_shouldReturnStatusBadRequest() throws Exception {
        // given
        FinancialTransactionCreateDTO financialTransactionCreateDTO = createFinancialTransactionCreateDTO();
        financialTransactionCreateDTO.setAmount(negativeAmount);
        User user = TestUtils.createUserForTest();

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(financialTransactionCreateDTO)))
                .characterEncoding("UTF-8")
                .principal(() -> USER_EMAIL));

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request status when the amount format is invalid")
    void createFinancialTransaction_invalidAmountFormat_shouldReturnStatusBadRequest() throws Exception {
        // given
        FinancialTransactionCreateDTO financialTransactionCreateDTO = createFinancialTransactionCreateDTO();
        financialTransactionCreateDTO.setAmount(invalidAmountFormat);
        User user = TestUtils.createUserForTest();

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(financialTransactionCreateDTO)))
                .characterEncoding("UTF-8")
                .principal(() -> USER_EMAIL));

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    private FinancialTransactionCreateDTO createFinancialTransactionCreateDTO() {
        return new FinancialTransactionCreateDTO(WALLET_ID_1, ONE, DESCRIPTION, EXPENSE,
                DATE, CATEGORY_ID);
    }

    private FinancialTransactionDTO createFinancialTransactionDTO() {
        return new FinancialTransactionDTO(ID_1L, ONE, DESCRIPTION, EXPENSE, DATE, CATEGORY_ID);
    }

}