package com.example.trainingsapp.wallet;

import com.example.trainingsapp.authorization.JwtAuthorizationFilter;
import com.example.trainingsapp.authorization.WebSecurityConfiguration;
import com.example.trainingsapp.authorization.api.MyUserDetailsService;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.wallet.api.WalletService;
import com.example.trainingsapp.wallet.api.dto.WalletDTO;
import com.example.trainingsapp.wallet.impl.WalletServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = WalletController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {WalletServiceImpl.class, WebSecurityConfiguration.class, JwtAuthorizationFilter.class,
                        MyUserDetailsService.class}),
        includeFilters = @ComponentScan.Filter())
class WalletControllerTest {
    public static final Long WALLET_ID_1L = 1L;
    public static final Long USER_ID_1L = 1L;
    public static final Instant INSTAND_NOW = Instant.now();
    public static final String WALLET_NAME = "Wallet Name";
    // MockMvc jest narzędziem do symulowania żądań HTTP do twoich kontrolerów. Umożliwia testowanie kontrolerów Spring
    // MVC bez konieczności uruchamiania pełnego serwera aplikacji.
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WalletService walletService;
    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createWallet() {
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
        WalletDTO wallet = new WalletDTO(WALLET_ID_1L, WALLET_NAME, INSTAND_NOW, USER_ID_1L);

        when(walletService.findById(1L, 1L))
                .thenReturn(Optional.of(wallet));
        this.mockMvc.perform(get("/api/wallets/1"))
                .andDo(print()).andExpect(status().isOk());
    }
}