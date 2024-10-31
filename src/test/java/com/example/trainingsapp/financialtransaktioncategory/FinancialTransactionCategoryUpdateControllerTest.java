package com.example.trainingsapp.financialtransaktioncategory;

import com.example.trainingsapp.TestUtils;
import com.example.trainingsapp.authorization.JwtAuthorizationFilter;
import com.example.trainingsapp.authorization.WebSecurityConfiguration;
import com.example.trainingsapp.authorization.api.MyUserDetailsService;
import com.example.trainingsapp.authorization.webtoken.JwtService;
import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryService;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryDTO;
import com.example.trainingsapp.financialtransaktioncategory.api.dto.FinancialTransactionCategoryUpdateDTO;
import com.example.trainingsapp.financialtransaktioncategory.impl.FinancialTransactionCategoryServiceImpl;
import com.example.trainingsapp.general.exception.ErrorStrategy;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.UserService;
import com.example.trainingsapp.user.api.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType.EXPENSE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

@WebMvcTest(controllers = FinancialTransactionCategoryController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {FinancialTransactionCategoryServiceImpl.class}),
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        ErrorStrategy.class, WebSecurityConfiguration.class, MyUserDetailsService.class,
                        JwtAuthorizationFilter.class, JwtService.class}))
class FinancialTransactionCategoryUpdateControllerTest {
    private static final Long ID_1L = 1L;
    private static final Long USER_ID_1L = 1L;
    private static final String USER_EMAIL = "example@email.com";
    private static final String EXAMPLE_CATEGORY_NAME = "Example category name_";
    private static final String CATEGORY_NAME_TO_LONG = "sdfasdfas4353432523m45bn4m5nbmnbm2345234";
    @MockBean
    private FinancialTransactionCategoryService financialTransactionCategoryService;
    @MockBean
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should returns financial transaction category DTO with updated parameters and status OK")
    @WithMockUser(username = USER_EMAIL)
    void updateFinancialTransactionCategory_forValidData_shouldReturnsFTCategory() throws Exception {
        // given
        User user = TestUtils.createUserForTest();
        when(userService.findUserByEmail(USER_EMAIL)).thenReturn(user);

        FinancialTransactionCategoryUpdateDTO categoryUpdateDTO =
                new FinancialTransactionCategoryUpdateDTO(EXAMPLE_CATEGORY_NAME, EXPENSE);

        FinancialTransactionCategoryDTO categoryDTO = TestUtils.createFinancialTransactionCategoryDTOForTest(
                EXPENSE, USER_ID_1L);

        when(financialTransactionCategoryService.updateFinancialTransactionCategory(
                ID_1L, categoryUpdateDTO, USER_ID_1L)).thenReturn(categoryDTO);

        // when
        ResultActions resultActions = mockMvc.perform(patch("/api/categories/{id}", ID_1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(categoryUpdateDTO))));

        // then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(ID_1L.intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(categoryDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(EXPENSE.toString()));

    }
}