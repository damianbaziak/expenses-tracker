package com.example.trainingsapp.financialtransaction;

import com.example.trainingsapp.TestUtils;
import com.example.trainingsapp.authorization.JwtAuthorizationFilter;
import com.example.trainingsapp.authorization.api.MyUserDetailsService;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionService;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionDTO;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionUpdateDTO;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import static com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType.EXPENSE;
import static com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType.INCOME;
import static java.math.BigDecimal.TEN;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FinancialTransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
class FinancialTransactionUpdateControllerTest {

    private static final Long ID_1L = 1L;
    private static final Long USER_ID_1L = 1L;
    private static final String USER_EMAIL = "user@example@email.com";
    private static final String DESCRIPTION = "Description";
    private static final String NEW_DESCRIPTION = "Updated description";
    private static final Instant DATE_NOW = Instant.now();
    private static final Instant NEW_DATE = Instant.parse("2030-12-22T14:30:00.500Z");
    private static final Long NEW_CATEGORY_ID = 2L;

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
        FinancialTransactionUpdateDTO ftUpdateDTO = createFinancialTransactionUpdateDTO();

        FinancialTransactionDTO ftDTO = new FinancialTransactionDTO(
                ID_1L, TEN, NEW_DESCRIPTION, INCOME, NEW_DATE, NEW_CATEGORY_ID);

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));

        when(financialTransactionService.updateFinancialTransaction(ID_1L, ftUpdateDTO, USER_ID_1L)).thenReturn(ftDTO);

        // when
        ResultActions resultActions = mockMvc.perform(patch("/api/transactions/{1}", ID_1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(ftUpdateDTO)))
                .principal(() -> USER_EMAIL));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(ftDTO.getId().intValue())))
                .andExpect(jsonPath("$.type", CoreMatchers.is(INCOME.name())))
                .andExpect(jsonPath("$.description", CoreMatchers.is(ftDTO.getDescription())));

    }

    private FinancialTransactionUpdateDTO createFinancialTransactionUpdateDTO() {
        return new FinancialTransactionUpdateDTO(TEN, NEW_DESCRIPTION, INCOME,
                NEW_DATE, NEW_CATEGORY_ID);
    }
}