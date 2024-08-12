package com.example.trainingsapp.wallet;

import com.example.trainingsapp.authorization.JwtAuthorizationFilter;
import com.example.trainingsapp.authorization.api.MyUserDetailsService;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.model.User;
import com.example.trainingsapp.wallet.api.WalletService;
import com.example.trainingsapp.wallet.api.dto.WalletCreateDTO;
import com.example.trainingsapp.wallet.api.dto.WalletDTO;
import com.example.trainingsapp.wallet.api.dto.WalletUpdateDTO;
import com.example.trainingsapp.wallet.model.Wallet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WalletController.class)
@AutoConfigureMockMvc(addFilters = false)
class WalletControllerTests {
    public static final Long WALLET_ID_1L = 1L;
    public static final Long WALLET_ID_2L = 2L;
    public static final Long WALLET_ID_3L = 3L;
    public static final Long USER_ID_1L = 1L;
    public static final Instant DATE_NOW = Instant.now();
    public static final String WALLET_NAME = "Test Wallet Name";
    public static final String USER_EMAIL = "user@example.email.com";
    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);
    @MockBean
    MyUserDetailsService myUserDetailsService;
    @MockBean
    JwtAuthorizationFilter jwtAuthorizationFilter;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WalletService walletService;
    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Should return status 201 - Created and wallet, when wallet is created correctly")
    public void createWallet_walletCreated_shouldReturnWalletAndStatusCreated() throws Exception {
        // given
        WalletCreateDTO createWalletDTO = new WalletCreateDTO(WALLET_NAME);
        User user = new User();
        user.setId(USER_ID_1L);
        user.setEmail(USER_EMAIL);
        WalletDTO walletDTO = new WalletDTO(WALLET_ID_1L, WALLET_NAME, DATE_NOW, USER_ID_1L);

        given(walletService.createWallet(createWalletDTO, USER_ID_1L)).willReturn(walletDTO);
        given(userRepository.findByEmail(USER_EMAIL)).willReturn(Optional.of(user));

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/wallets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(createWalletDTO)))
                .characterEncoding("UTF-8")
                .principal(() -> USER_EMAIL));

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", CoreMatchers.is(WALLET_NAME)))
                .andExpect(jsonPath("$.id", CoreMatchers.is(WALLET_ID_1L.intValue())));
    }

    @Test
    @DisplayName("Should return status 200 - OK and a String as the Body when wallet is deleted correctly ")
    void deleteWalletById_walletDeleted_shouldReturnStatusOK() throws Exception {
        // given
        User user = User.builder()
                .id(USER_ID_1L)
                .email(USER_EMAIL)
                .build();
        doNothing().when(walletService).deleteWallet(WALLET_ID_1L, USER_ID_1L);
        given(userRepository.findByEmail(USER_EMAIL)).willReturn(Optional.of(user));

        // when
        ResultActions resultActions = mockMvc.perform(delete("/api/wallets/{1}", WALLET_ID_1L)
                .contentType(MediaType.APPLICATION_JSON)
                .principal(() -> USER_EMAIL));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Wallet deleted successfully"));

        Mockito.verify(walletService, Mockito.times(1))
                .deleteWallet(WALLET_ID_1L, USER_ID_1L);

    }

    @Test
    @DisplayName("Should return correct response body and status OK when wallet name is changed")
    void updateWalletName_walletUpdated_shouldReturnWalletAndStatusOK() throws Exception {
        // given
        WalletUpdateDTO walletUpdateDTO = new WalletUpdateDTO(WALLET_NAME);
        User user = User.builder()
                .id(USER_ID_1L)
                .email(USER_EMAIL)
                .build();
        WalletDTO walletDTO = new WalletDTO(WALLET_ID_1L, WALLET_NAME, DATE_NOW, USER_ID_1L);

        given(walletService.updateWallet(WALLET_ID_1L, walletUpdateDTO, USER_ID_1L)).willReturn(walletDTO);
        given(userRepository.findByEmail(USER_EMAIL)).willReturn(Optional.of(user));

        // when
        ResultActions resultActions = mockMvc.perform(patch("/api/wallets/{1}", WALLET_ID_1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(walletUpdateDTO)))
                .characterEncoding("UTF-8")
                .principal(() -> USER_EMAIL));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.is(WALLET_NAME)))
                .andExpect(jsonPath("$.userId", CoreMatchers.is(USER_ID_1L.intValue())));


    }
/*
    @Test
    void getWallets() throws Exception {
        // given
        User user = new User();
        user.setId(USER_ID_1L);
        user.setEmail(USER_EMAIL);

        List<Wallet> wallets = new ArrayList<>()
        Mockito.when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));
        Mockito.when(walletService.getWallets(USER_ID_1L)).thenReturn(wallets);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/wallets")
                        .principal(() -> USER_EMAIL))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(WALLET_ID_1L.intValue()))
                .andExpect(jsonPath("$.name").value(WALLET_NAME));


    }

 */


    @Test
    @DisplayName("Should return wallet and status OK when finding by ID")
    void getWalletById_walletExist_shouldReturnWalletAndStatusOk() throws Exception {
        // given
        User user = new User();
        user.setId(USER_ID_1L);
        user.setEmail(USER_EMAIL);

        WalletDTO wallet = new WalletDTO(WALLET_ID_1L, WALLET_NAME, DATE_NOW, USER_ID_1L);

        Mockito.when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));
        Mockito.when(walletService.findById(WALLET_ID_1L, USER_ID_1L)).thenReturn(wallet);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/wallets/1")
                        .principal(() -> USER_EMAIL))
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(WALLET_ID_1L.intValue()))
                .andExpect(jsonPath("$.name").value(WALLET_NAME));


    }
}