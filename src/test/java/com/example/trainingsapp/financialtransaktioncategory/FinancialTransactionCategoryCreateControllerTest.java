package com.example.trainingsapp.financialtransaktioncategory;

import com.example.trainingsapp.TestUtils;
import com.example.trainingsapp.authorization.JwtAuthorizationFilter;
import com.example.trainingsapp.authorization.api.MyUserDetailsService;
import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryService;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryCreateDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryDTO;
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

import java.util.Objects;
import java.util.Optional;

import static com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType.EXPENSE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FinancialTransactionCategoryController.class, includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = {ErrorStrategy.class}))
@AutoConfigureMockMvc(addFilters = false)
class FinancialTransactionCategoryCreateControllerTest {

    private static final Long ID_1L = 1L;
    private static final Long USER_ID_1L = 1L;
    private static final String USER_EMAIL = "example@email.com";
    private static final String DESCRIPTION = "Example description";
    private static final String EXAMPLE_CATEGORY_NAME = "Example category name";
    private static final Long CATEGORY_ID = 1L;

    @MockBean
    private FinancialTransactionCategoryService financialTransactionCategoryService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private MyUserDetailsService myUserDetailsService;
    @MockBean
    private JwtAuthorizationFilter jwtAuthorizationFilter;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Should returns status created - 201 and financial transaction category")
    void createCategory_forValidParameters_shouldReturnsFTCDTO() throws Exception {
        // given
        User user = TestUtils.createUserForTest();
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));

        FinancialTransactionCategoryCreateDTO financialTransactionCategoryCreateDTO =
                new FinancialTransactionCategoryCreateDTO(EXAMPLE_CATEGORY_NAME, EXPENSE);

        FinancialTransactionCategoryDTO financialTransactionCategoryDTO =
                TestUtils.createFinancialTransactionCategoryDTOForTest(EXPENSE, USER_ID_1L);

        when(financialTransactionCategoryService.createCategory(financialTransactionCategoryCreateDTO, USER_ID_1L))
                .thenReturn(financialTransactionCategoryDTO);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(objectMapper.writeValueAsString(financialTransactionCategoryCreateDTO)))
                        .principal(() -> USER_EMAIL)
                        .characterEncoding("UTF-8"))
                .andDo(print());

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", CoreMatchers.is(ID_1L.intValue())))
                .andExpect(jsonPath("$.name", CoreMatchers.is(EXAMPLE_CATEGORY_NAME)))
                .andExpect(jsonPath("$.type", CoreMatchers.is(EXPENSE.name())));


    }

}