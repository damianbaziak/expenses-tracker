package com.example.trainingsapp.financialtransaction;

import com.example.trainingsapp.authorization.JwtAuthorizationFilter;
import com.example.trainingsapp.authorization.api.MyUserDetailsService;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionService;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionCreateDTO;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionDTO;
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

import static com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType.EXPENSE;
import static java.math.BigDecimal.ONE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FinancialTransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class FinancialTransactionCreateControllerTest {
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
    private static final String USER_EMAIL = "user@example@email.com";
    private static final String DESCRIPTION = "Example description";
    private static final Long CATEGORY_ID = 1L;
    private static final Instant DATE_NOW = Instant.now();
    private static final BigDecimal negativeAmount = BigDecimal.valueOf(-100.00);
    private static final BigDecimal wrongFormatAmount = BigDecimal.valueOf(98.3974932);
    private static final Long WALLET_ID_1 = 1L;


    // TEST FAILS. TEST RETURNS STATUS CREATED - 201 BUT DOES NOT RETURN FINANCIAL STATUS AS A BODY.
    @Test
    @DisplayName("Should return financial transaction and status 201-Created")
    void createFinancialTransaction_validData_shouldReturnFinancialTransactionAndStatusCreated() throws Exception {
        // given
        User user = createUserForTest();
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
                .andExpect(jsonPath("$.type", CoreMatchers.is(EXPENSE)));
    }

    @Test
    @DisplayName("Should return bad request status when financialTransactionType is null")
    void createFinancialTransaction_FinancialTransactionTypeNull_shouldReturnStatusBadRequest() throws Exception {
        // given
        FinancialTransactionCreateDTO financialTransactionCreateDTO = createFinancialTransactionCreateDTO();
        financialTransactionCreateDTO.setType(null);
        User user = createUserForTest();
        FinancialTransactionDTO financialTransactionDTO = createFinancialTransactionDTO();

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
        User user = createUserForTest();
        FinancialTransactionDTO financialTransactionDTO = createFinancialTransactionDTO();

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
    @DisplayName("Should return bad request status when amount format is wrong")
    void createFinancialTransaction_wrongFormatAmount_shouldReturnStatusBadRequest() throws Exception {
        // given
        FinancialTransactionCreateDTO financialTransactionCreateDTO = createFinancialTransactionCreateDTO();
        financialTransactionCreateDTO.setAmount(wrongFormatAmount);
        User user = createUserForTest();
        FinancialTransactionDTO financialTransactionDTO = createFinancialTransactionDTO();

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
                DATE_NOW, CATEGORY_ID);
    }

    private FinancialTransactionDTO createFinancialTransactionDTO() {
        return new FinancialTransactionDTO(ID_1L, ONE, DESCRIPTION, EXPENSE, DATE_NOW, CATEGORY_ID);
    }

    private User createUserForTest() {
        return User.builder()
                .id(USER_ID_1L)
                .email(USER_EMAIL)
                .build();
    }
}