package com.example.trainingsapp.wallet;

import com.example.trainingsapp.authorization.JwtAuthorizationFilter;
import com.example.trainingsapp.authorization.WebSecurityConfiguration;
import com.example.trainingsapp.authorization.api.MyUserDetailsService;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.model.User;
import com.example.trainingsapp.wallet.api.WalletService;
import com.example.trainingsapp.wallet.api.dto.WalletCreateDTO;
import com.example.trainingsapp.wallet.api.dto.WalletDTO;
import com.example.trainingsapp.wallet.impl.WalletServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(controllers = WalletController.class)
@Import(WebSecurityConfiguration.class)
@ExtendWith(MockitoExtension.class)
class WalletControllerTest {
    public static final Long WALLET_ID_1L = 1L;
    public static final Long USER_ID_1L = 1L;
    public static final Instant DATE_NOW = Instant.now();
    public static final String WALLET_NAME = "Wallet Name";
    public static final String USER_EMAIL = "user@email.com";
    @MockBean
    MyUserDetailsService myUserDetailsService;
    @MockBean
    JwtAuthorizationFilter jwtAuthorizationFilter;
    // MockMvc jest narzędziem do symulowania żądań HTTP do twoich kontrolerów. Umożliwia testowanie kontrolerów Spring
    // MVC bez konieczności uruchamiania pełnego serwera aplikacji
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WalletService walletService;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createWallet() throws Exception {
        // given
        User user = new User();
        user.setId(USER_ID_1L);
        user.setEmail(USER_EMAIL);
        given(userRepository.findByEmail(USER_EMAIL)).willReturn(Optional.of(user));
        WalletDTO walletDTO = new WalletDTO(WALLET_ID_1L,
                WALLET_NAME, DATE_NOW, USER_ID_1L);
        WalletCreateDTO walletCreateDTO = new WalletCreateDTO(WALLET_NAME);
        given(walletService.createWallet(walletCreateDTO, USER_ID_1L)).willReturn(walletDTO);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/wallets()")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(walletCreateDTO)))
                .with(SecurityMockMvcRequestPostProcessors.user(USER_EMAIL)));

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
    }

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
    void testGetWalletById() throws Exception {
        // given
       /* User user = new User();
        user.setId(USER_ID_1L);
        user.setEmail(USER_EMAIL);
        given(userRepository.findByEmail(USER_EMAIL)).willReturn(Optional.of(user));

        */
        WalletDTO wallet = new WalletDTO(WALLET_ID_1L, WALLET_NAME, DATE_NOW, USER_ID_1L);

        given(walletService.findById(1L, 1L)).willReturn(Optional.of(wallet));
        ResultActions resultActions = mockMvc.perform(get("/api/wallets/1")
                .with(SecurityMockMvcRequestPostProcessors.user(String.valueOf(USER_ID_1L))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}