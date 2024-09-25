package com.example.trainingsapp.financialtransaction;

import com.example.trainingsapp.TestUtils;
import com.example.trainingsapp.authorization.JwtAuthorizationFilter;
import com.example.trainingsapp.authorization.api.MyUserDetailsService;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionService;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionDTO;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionUpdateDTO;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import static com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType.INCOME;
import static java.math.BigDecimal.TEN;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FinancialTransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
class FinancialTransactionUpdateControllerTest {

    private static final Long ID_1L = 1L;
    private static final Long USER_ID_1L = 1L;
    private static final String USER_EMAIL = "example@email.com";
    private static final String DESCRIPTION = "Description";
    private static final Instant DATE = Instant.parse("2024-12-22T14:30:00.500Z");
    private static final Long CATEGORY_ID = 2L;
    private static final BigDecimal negativeAmount = BigDecimal.valueOf(-100.00);
    private static final BigDecimal invalidAmountFormat = BigDecimal.valueOf(98.3974932);

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

    @Test
    @DisplayName("Should return status OK and financial transaction")
    void updateFinancialTransaction_validData_shouldReturnFinancialTransactionAndStatusOK() throws Exception {
        // given
        User user = TestUtils.createUserForTest();
        FinancialTransactionUpdateDTO ftUpdateDTO = createFinancialTransactionUpdate();

        FinancialTransactionDTO ftDTO = new FinancialTransactionDTO(
                ID_1L, TEN, DESCRIPTION, INCOME, DATE, CATEGORY_ID);

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));

        when(financialTransactionService.updateFinancialTransaction(ID_1L, ftUpdateDTO, USER_ID_1L)).thenReturn(ftDTO);

        // when
        ResultActions resultActions = mockMvc.perform(patch("/api/transactions/{1}", ID_1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(ftUpdateDTO)))
                .characterEncoding("UTF-8")
                .principal(() -> USER_EMAIL)).andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(ftDTO.getId().intValue())))
                .andExpect(jsonPath("$.type", CoreMatchers.is(INCOME.name())))
                .andExpect(jsonPath("$.description", CoreMatchers.is(ftDTO.getDescription())));

    }

    @Test
    @DisplayName("Should return bad request status when financialTransactionType is null")
    void updateFinancialTransaction_financialTransactionTypeNull_shouldReturnStatusBadRequest() throws Exception {
        // given
        FinancialTransactionUpdateDTO financialTransactionUpdateDTO = createFinancialTransactionUpdate();
        financialTransactionUpdateDTO.setType(null);
        User user = TestUtils.createUserForTest();

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));

        // when
        ResultActions resultActions = mockMvc.perform(patch("/api/transactions/{id}", ID_1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(financialTransactionUpdateDTO)))
                .characterEncoding("UTF-8")
                .principal(() -> USER_EMAIL));

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request status when the amount format is invalid")
    void updateFinancialTransaction_invalidAmountFormat_shouldReturnStatusBadRequest() throws Exception {
        // given
        FinancialTransactionUpdateDTO financialTransactionUpdateDTO = createFinancialTransactionUpdate();
        financialTransactionUpdateDTO.setAmount(invalidAmountFormat);
        User user = TestUtils.createUserForTest();

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));

        // when
        ResultActions resultActions = mockMvc.perform(patch("/api/transactions/{id}", ID_1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(financialTransactionUpdateDTO)))
                .characterEncoding("UTF-8")
                .principal(() -> USER_EMAIL));

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request status when amount is negative")
    void updateFinancialTransaction_negativeAmount_shouldReturnStatusBadRequest() throws Exception {
        // given
        FinancialTransactionUpdateDTO financialTransactionUpdateDTO = createFinancialTransactionUpdate();
        financialTransactionUpdateDTO.setAmount(negativeAmount);
        User user = TestUtils.createUserForTest();

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));

        // when
        ResultActions resultActions = mockMvc.perform(patch("/api/transactions/{id}", ID_1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(financialTransactionUpdateDTO)))
                .characterEncoding("UTF-8")
                .principal(() -> USER_EMAIL));

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    private FinancialTransactionUpdateDTO createFinancialTransactionUpdate() {
        return new FinancialTransactionUpdateDTO(TEN, DESCRIPTION, INCOME, DATE, CATEGORY_ID);
    }

}