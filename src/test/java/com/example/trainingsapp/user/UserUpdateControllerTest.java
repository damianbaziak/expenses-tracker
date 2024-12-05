package com.example.trainingsapp.user;

import com.example.trainingsapp.TestUtils;
import com.example.trainingsapp.authorization.JwtAuthorizationFilter;
import com.example.trainingsapp.authorization.api.MyUserDetailsService;
import com.example.trainingsapp.authorization.webtoken.JwtService;
import com.example.trainingsapp.general.exception.ErrorStrategy;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.UserService;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.api.dto.UserUsernameUpdateDTO;
import com.example.trainingsapp.user.impl.UserServiceImpl;
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

import java.util.Objects;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class,
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {JwtService.class, MyUserDetailsService.class, JwtAuthorizationFilter.class}),
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {UserServiceImpl.class}))
class UserUpdateControllerTest {

    private static final Long ID_1L = 1L;
    private static final String USER_EMAIL = "example@email.com";
    private static final String USER_FIRSTNAME = "Firstname_Example";
    private static final String USER_LASTNAME = "Lastname_Example";
    private static final String USER_PASSWORD = "52345hg23jk4h5";
    private static final String USER_USERNAME = "Username_Example";
    private static final int USER_AGE = 30;
    private static final String NEW_USERNAME = "New_Username";
    private static final String NEW_PASSWORD = "New_Password";
    private static final String NEW_EMAIL = "new_email@email.com";

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ErrorStrategy errorStrategy;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = USER_EMAIL)
    @DisplayName("Should return HTTP status OK and userDTO with new username for valid input")
    void updateUsername_validInput_shouldReturnUpdatedUser() throws Exception {
        // given
        User userPrincipal = TestUtils.createUserForTest(USER_EMAIL);
        when(userService.findUserByEmail(USER_EMAIL)).thenReturn(userPrincipal);

        UserUsernameUpdateDTO updateDTO = new UserUsernameUpdateDTO(NEW_USERNAME);

        UserDTO userDTO = new UserDTO(
                USER_FIRSTNAME, USER_LASTNAME, USER_AGE, USER_EMAIL, NEW_USERNAME, USER_PASSWORD);

        when(userService.updateUsername(ID_1L, updateDTO, userPrincipal.getId())).thenReturn(userDTO);

        // when
        ResultActions result = mockMvc.perform(patch("/api/users/{id}/username", ID_1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(updateDTO))));

        // then
        result.andExpect(status().isOk());
        result.andExpect(content().string(new ObjectMapper().writeValueAsString(userDTO)));
    }

    @Test
    void updateEmail() {
    }

    @Test
    void updatePassword() {
    }
}