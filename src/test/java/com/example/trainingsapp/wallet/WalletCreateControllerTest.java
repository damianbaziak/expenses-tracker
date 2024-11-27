package com.example.trainingsapp.wallet;

import com.example.trainingsapp.TestUtils;
import com.example.trainingsapp.authorization.JwtAuthorizationFilter;
import com.example.trainingsapp.authorization.WebSecurityConfiguration;
import com.example.trainingsapp.authorization.api.MyUserDetailsService;
import com.example.trainingsapp.authorization.webtoken.JwtService;
import com.example.trainingsapp.general.exception.ErrorStrategy;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.UserService;
import com.example.trainingsapp.user.api.model.User;
import com.example.trainingsapp.wallet.api.dto.WalletCreateDTO;
import com.example.trainingsapp.wallet.api.dto.WalletDTO;
import com.example.trainingsapp.wallet.impl.WalletServiceImpl;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WalletController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {WalletServiceImpl.class}),
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        ErrorStrategy.class, WebSecurityConfiguration.class, MyUserDetailsService.class,
                        JwtAuthorizationFilter.class, JwtService.class}))
class WalletCreateControllerTest {
    private static final String USER_EMAIL = "example@email.com";
    private static final Long USER_ID_1L = 1L;
    private static final String WALLET_NAME_TO_LONG = "Example wallet name_wallet name to long";
    private static final String WALLET_NAME_TO_SHORT = "W";
    private static final String EXAMPLE_WALLET_NAME = "Example wallet name_";
    private static final Long WALLET_ID_1L = 1L;


    @MockBean
    private WalletServiceImpl walletService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = USER_EMAIL)
    @DisplayName("Should return status 201 Created and walletDTO for valid input")
    void createWallet_withValidData_ReturnsCreatedWithWalletDTO() throws Exception {
        // given
        User user = TestUtils.createUserForTest(USER_EMAIL);
        WalletDTO walletDTO = TestUtils.createWalletDTOForTest(USER_ID_1L);
        WalletCreateDTO createDTO = new WalletCreateDTO(EXAMPLE_WALLET_NAME);

        when(userService.findUserByEmail(USER_EMAIL)).thenReturn(user);

        when(walletService.createWallet(createDTO, USER_ID_1L)).thenReturn(walletDTO);

        // when
        ResultActions result = mockMvc.perform(post("/api/wallets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(createDTO))));

        // then
        result
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(WALLET_ID_1L.intValue())))
                .andExpect(jsonPath("$.name", is(createDTO.getName())))
                // The newly created wallet initially has the balance set to BigDecimal.ZERO.
                .andExpect(jsonPath("$.balance").value(0));

    }

    @Test
    @WithMockUser(username = USER_EMAIL)
    @DisplayName("Should return status Bad Request when input wallet name is to long")
    void createWallet_walletNameToLong_ReturnsAnException() throws Exception {
        // given
        User user = TestUtils.createUserForTest(USER_EMAIL);
        WalletCreateDTO createDTO = new WalletCreateDTO(WALLET_NAME_TO_LONG);

        when(userService.findUserByEmail(USER_EMAIL)).thenReturn(user);

        // when
        ResultActions result = mockMvc.perform(post("/api/wallets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(createDTO))));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("Name should be between 2 and 20 characters long")));


    }

    @Test
    @WithMockUser(username = USER_EMAIL)
    @DisplayName("Should return status Bad Request when input wallet name is to short")
    void createWallet_walletNameToShort_ReturnsAnException() throws Exception {
        // given
        User user = TestUtils.createUserForTest(USER_EMAIL);
        WalletCreateDTO createDTO = new WalletCreateDTO(WALLET_NAME_TO_SHORT);

        when(userService.findUserByEmail(USER_EMAIL)).thenReturn(user);

        // when
        ResultActions result = mockMvc.perform(post("/api/wallets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(createDTO))));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("Name should be between 2 and 20 characters long")));
    }

    @Test
    @WithMockUser(username = USER_EMAIL)
    @DisplayName("Should return status Bad Request when input wallet name is blank")
    void createWallet_walletNameIsBlank_ReturnsAnException() throws Exception {
        // given
        User user = TestUtils.createUserForTest(USER_EMAIL);
        WalletCreateDTO createDTO = new WalletCreateDTO("        ");

        when(userService.findUserByEmail(USER_EMAIL)).thenReturn(user);

        // when
        ResultActions result = mockMvc.perform(post("/api/wallets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(createDTO))));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("Name can not be blank")));

    }



}