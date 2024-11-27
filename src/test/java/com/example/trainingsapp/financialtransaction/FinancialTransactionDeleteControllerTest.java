package com.example.trainingsapp.financialtransaction;

import com.example.trainingsapp.TestUtils;
import com.example.trainingsapp.authorization.JwtAuthorizationFilter;
import com.example.trainingsapp.authorization.WebSecurityConfiguration;
import com.example.trainingsapp.authorization.api.MyUserDetailsService;
import com.example.trainingsapp.authorization.webtoken.JwtService;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionRepository;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionService;
import com.example.trainingsapp.financialtransaction.impl.FinancialTransactionServiceImpl;
import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.general.exception.ErrorStrategy;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.UserService;
import com.example.trainingsapp.user.api.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FinancialTransactionController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {FinancialTransactionServiceImpl.class}),
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        ErrorStrategy.class, WebSecurityConfiguration.class, MyUserDetailsService.class,
                        JwtAuthorizationFilter.class, JwtService.class}))
class FinancialTransactionDeleteControllerTest {
    private static final Long TRANSACTION_ID_1 = 1L;
    private static final Long ID_0 = 0L;
    private static final String USER_EMAIL = "example@email.com";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FinancialTransactionRepository financialTransactionRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private FinancialTransactionService financialTransactionService;

    @Test
    @WithMockUser(username = USER_EMAIL)
    @DisplayName("Should return status OK and a String as a body when transaction deleted correctly")
    void deleteTransactionById_correctDeletion_shouldReturnStatusOKAndStringAsBody() throws Exception {
        // given
        User user = TestUtils.createUserForTest();
        when(userService.findUserByEmail(USER_EMAIL)).thenReturn(user);

        doNothing().when(financialTransactionService).deleteTransaction(TRANSACTION_ID_1, user.getId());

        // when
        ResultActions resultActions = mockMvc.perform(delete("/api/transactions/{id}", TRANSACTION_ID_1));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Transaction deleted successfully"));
        verify(financialTransactionService, times(1)).deleteTransaction(
                TRANSACTION_ID_1, user.getId());
    }

    @Test
    @WithMockUser(username = USER_EMAIL)
    @DisplayName("Should return status 404 when financial transaction ID not exist")
    void deleteTransactionById_transactionNotExist_shouldReturnStatusNotFound() throws Exception {
        // given
        User user = TestUtils.createUserForTest();
        when(userService.findUserByEmail(USER_EMAIL)).thenReturn(user);

        doThrow(new AppRuntimeException(ErrorCode.FT001, "Transaction not found")).when(
                financialTransactionService).deleteTransaction(TRANSACTION_ID_1, user.getId());

        // when
        ResultActions result = mockMvc.perform(delete("/api/transactions/{id}", TRANSACTION_ID_1));

        // then
        result
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Transaction not found"));
    }

    @Test
    @WithMockUser(username = USER_EMAIL)
    @DisplayName("Should return bad request status when financial transaction ID is zero")
    void deleteTransactionById_transactionIdIsZero_shouldReturnStatusBadRequest() throws Exception {
        // given
        User user = TestUtils.createUserForTest();
        when(userService.findUserByEmail(USER_EMAIL)).thenReturn(user);

        // when
        ResultActions result = mockMvc.perform(delete("/api/transactions/{id}", ID_0));

        // then
        result.andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("$.message").value(ErrorCode.TEA001.getBusinessMessage()),
                MockMvcResultMatchers.jsonPath("$.status").value(ErrorCode.TEA001.getBusinessStatus()),
                MockMvcResultMatchers.jsonPath("$.statusCode").value(ErrorCode.TEA001.getHttpStatusCode()));
    }
}

