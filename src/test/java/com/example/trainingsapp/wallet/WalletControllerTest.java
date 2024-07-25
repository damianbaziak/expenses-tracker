package com.example.trainingsapp.wallet;

import com.example.trainingsapp.authorization.JwtAuthorizationFilter;
import com.example.trainingsapp.authorization.WebSecurityConfiguration;
import com.example.trainingsapp.authorization.api.MyUserDetailsService;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.model.User;
import com.example.trainingsapp.wallet.api.WalletService;
import com.example.trainingsapp.wallet.api.dto.WalletCreateDTO;
import com.example.trainingsapp.wallet.api.dto.WalletDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = WalletController.class)
@Import(WebSecurityConfiguration.class)
class WalletControllerTest {
    public static final Long WALLET_ID_1L = 1L;
    public static final Long USER_ID_1L = 1L;
    public static final Instant DATE_NOW = Instant.now();
    public static final String WALLET_NAME = "Wallet Name";
    public static final String USER_EMAIL = "user@email.com";
    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);
    @MockBean
    MyUserDetailsService myUserDetailsService;
    @MockBean
    JwtAuthorizationFilter jwtAuthorizationFilter;
    @Autowired
    ObjectMapper objectMapper;
    // MockMvc jest narzędziem do symulowania żądań HTTP do twoich kontrolerów. Umożliwia testowanie kontrolerów Spring
    // MVC bez konieczności uruchamiania pełnego serwera aplikacji
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WalletService walletService;
    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Should return status 201 - Created, when wallet is created correctly")
    public void createWallet_shouldReturnStatusCreated() throws Exception {
        // given
        WalletCreateDTO createWalletDTO = new WalletCreateDTO(WALLET_NAME);

        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setId(WALLET_ID_1L);
        walletDTO.setName(WALLET_NAME);
        walletDTO.setCreationDate(DATE_NOW);
        walletDTO.setUserId(USER_ID_1L);

        User user = new User();
        user.setId(USER_ID_1L);
        user.setEmail(USER_EMAIL);

        given(walletService.createWallet(createWalletDTO, USER_ID_1L)).willReturn(walletDTO);
        given(userRepository.findByEmail(USER_EMAIL)).willReturn(Optional.of(user));

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/wallets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createWalletDTO))
                .with(SecurityMockMvcRequestPostProcessors.user(USER_EMAIL)));

        resultActions.andDo(result -> {
            System.out.println("Response body: " + result.getResponse().getContentAsString());
        });
        // then
        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(jsonPath("$.name").value(WALLET_NAME));
    }


    /*
    @Test
    void createWallet() throws Exception {
        // given
        User user = new User();
        user.setId(USER_ID_1L);
        user.setEmail(USER_EMAIL);
        given(userRepository.findByEmail(USER_EMAIL)).willReturn(Optional.of(user));
        WalletCreateDTO walletCreateDTO = new WalletCreateDTO(WALLET_NAME);
        WalletDTO walletDTO = new WalletDTO(WALLET_ID_1L,
                WALLET_NAME, DATE_NOW, USER_ID_1L);
        given(walletService.createWallet(walletCreateDTO, USER_ID_1L)).willReturn(walletDTO);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/wallets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(walletCreateDTO))
                        .principal(() ->"user@email.com"));
                //.with(SecurityMockMvcRequestPostProcessors.user(USER_EMAIL)));

        // then
        resultActions.andExpect(status().isCreated());
    }

     */


    @Test
    void deleteWallet() {
    }

    @Test
    void updateWallet() {
    }

    @Test
    void getWallets() {
    }

    @Test
    @DisplayName("Should return wallet and status OK when finding by ID")
    void getWalletById_walletExist_shouldReturnWalletAndStatusOk() throws Exception {
        // given
        User user = new User();
        user.setId(USER_ID_1L);
        user.setEmail(USER_EMAIL);

        WalletDTO wallet = new WalletDTO(WALLET_ID_1L, WALLET_NAME, DATE_NOW, USER_ID_1L);

        given(userRepository.findByEmail(USER_EMAIL)).willReturn(Optional.of(user));
        given(walletService.findById(WALLET_ID_1L, USER_ID_1L)).willReturn(wallet);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/wallets/1")
                .with(SecurityMockMvcRequestPostProcessors.user(USER_EMAIL)));


        resultActions.andDo(result -> {
            System.out.println("Response body: " + result.getResponse().getContentAsString());
        });

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(WALLET_ID_1L.intValue()));


    }
}