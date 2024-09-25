package com.example.trainingsapp.financialtransaction;

import com.example.trainingsapp.TestUtils;
import com.example.trainingsapp.authorization.JwtAuthorizationFilter;
import com.example.trainingsapp.authorization.api.MyUserDetailsService;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionRepository;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionService;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = FinancialTransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
class FinancialTransactionDeleteControllerTest {
    private static final Long TRANSACTION_ID_1 = 1L;
    private static final String USER_EMAIL = "example@email.com";

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private FinancialTransactionService financialTransactionService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MyUserDetailsService myUserDetailsService;

    @MockBean
    JwtAuthorizationFilter jwtAuthorizationFilterM;

    @MockBean
    FinancialTransactionRepository financialTransactionRepository;


    @Test
    @DisplayName("Should return status OK and a String as a body when transaction deleted correctly")
    void deleteTransactionById_correctDeletion_shouldReturnStatusOKAndStringAsBody() throws Exception {
        // given
        User user = TestUtils.createUserForTest();
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));
        doNothing().when(financialTransactionService).deleteTransaction(TRANSACTION_ID_1, user.getId());

        // when
        ResultActions resultActions = mockMvc.perform(delete("/api/transactions/{id}", TRANSACTION_ID_1)
                .principal(() -> USER_EMAIL))
                .andDo(print());

        // then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Transaction deleted successfully"));
        verify(financialTransactionService, times(1)).deleteTransaction(
                TRANSACTION_ID_1, user.getId());
    }
}