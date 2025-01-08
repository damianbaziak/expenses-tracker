package com.example.trainingsapp.authorization;

import com.example.trainingsapp.authorization.api.AuthService;
import com.example.trainingsapp.authorization.api.MyUserDetailsService;
import com.example.trainingsapp.authorization.impl.AuthServiceImpl;
import com.example.trainingsapp.authorization.webtoken.JwtService;
import com.example.trainingsapp.general.exception.ErrorStrategy;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = AuthServiceImpl.class),
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        ErrorStrategy.class, WebSecurityConfiguration.class, MyUserDetailsService.class,
                        JwtAuthorizationFilter.class, JwtService.class}))
class AuthRegisterControllerTest {

    private static final String EMAIL = "example@email.com";
    private static final String FIRSTNAME = "Example firstname";
    private static final String LASTNAME = "example@email.com";
    private static final Integer AGE = 20;
    private static final String USERNAME = "Example username";
    private static final String PASSWORD = "String10ch";
    private static final String TO_LONG_USERNAME = "fdsgsDAg345_4534sdfasdfds";
    private static final String PASSWORD_1_CHAR_MORE = "0123456789.";

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return status Created - 201 and user DTO when credentials are valid")
    void registerUser_validCredentials_shouldReturnStatus201() throws Exception {
        // given
        UserDTO userDTO = new UserDTO(FIRSTNAME, LASTNAME, AGE, EMAIL,
                USERNAME, PASSWORD);

        // when
        ResultActions result = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        // then
        result.andExpect(status().isCreated());
        result.andExpect(content().string("User created correctly"));

    }
}