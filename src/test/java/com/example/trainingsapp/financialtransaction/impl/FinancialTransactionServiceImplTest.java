package com.example.trainingsapp.financialtransaction.impl;

import com.example.trainingsapp.financialtransaction.api.FinancialTransactionModelMapper;
import com.example.trainingsapp.financialtransaction.api.FinancialTransactionRepository;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionCreateDTO;
import com.example.trainingsapp.financialtransaction.api.dto.FinancialTransactionDTO;
import com.example.trainingsapp.financialtransaction.api.model.FinancialTransaction;
import com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType;
import com.example.trainingsapp.financialtransaktioncategory.api.FinancialTransactionCategoryRepository;
import com.example.trainingsapp.financialtransaktioncategory.api.model.FinancialTransactionCategory;
import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.model.User;
import com.example.trainingsapp.wallet.api.WalletRepository;
import com.example.trainingsapp.wallet.api.model.Wallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Instant;
import java.util.Optional;

import static com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType.EXPENSE;
import static com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType.INCOME;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TWO;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

// Mockito Initialization:
// @ExtendWith(MockitoExtension.class) tells JUnit 5 to use Mockito's extension when running the test. This extension is responsible
// for initializing and configuring all fields annotated with Mockito annotations (such as @Mock, @InjectMocks, etc.) before each test method is executed.
// Automatic Mock Creation and Injection:
// The extension automatically creates mock instances for fields annotated with @Mock.
// It injects these mock instances into the fields of the class under test that are annotated with @InjectMocks, allowing the class to be tested with mocked dependencies.
// Support for Additional Mockito Features:
// The extension also supports other Mockito annotations like @Captor (for capturing method arguments) and @Spy (for partial mocking of real objects).
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FinancialTransactionServiceImplTest {
    private static final Long ID_1L = 1L;
    private static final String DESCRIPTION = "Description";
    private static final Instant DATE_NOW = Instant.now();
    private static final Long CATEGORY_ID = 1L;
    private static final Long WALLET_ID_1L = 1L ;


    @Mock
    private FinancialTransactionRepository ftRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private FinancialTransactionModelMapper financialTransactionModelMapper;

    @Mock
    private FinancialTransactionCategoryRepository financialTransactionCategoryRepository;

    @InjectMocks
    private FinancialTransactionServiceImpl financialTransactionService;

    @Test
    @DisplayName("Should return financial transaction with valid parameters")
    void CreateFinancialTransaction_ValidParameters_ReturnFinancialTransactionDTO() {
        // given
        User user = createUserForTest();

        FinancialTransactionCreateDTO financialTransactionCreateDTO = createFinancialTransactionCreateDTO();

        Wallet wallet = new Wallet();
        when(walletRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.of(wallet));

        FinancialTransaction financialTransactionEntity = createFinancialTransactionEntity(ID_1L);
        when(ftRepository.save(Mockito.any(FinancialTransaction.class))).thenReturn(financialTransactionEntity);

        FinancialTransactionDTO financialTransactionDTO = createFinancialTransactionDTO();
        when(financialTransactionModelMapper.mapFinancialTransactionEntityToFinancialTransactionDTO(
                any(FinancialTransaction.class))).thenReturn(financialTransactionDTO);

        FinancialTransactionCategory financialTransactionCategory = createFinancialTransactionCategory(EXPENSE, user);
        when(financialTransactionCategoryRepository.findByIdAndUserId(any(), any())).thenReturn(
                Optional.ofNullable(financialTransactionCategory));

        // when
        FinancialTransactionDTO result = financialTransactionService.createFinancialTransaction(
                financialTransactionCreateDTO, user.getId());

        // then
        Assertions.assertAll(() -> assertEquals(financialTransactionDTO, result), () -> assertEquals(
                financialTransactionDTO.getId(), result.getId()));
        verify(ftRepository, atMostOnce()).save(any(FinancialTransaction.class));
        verify(walletRepository, atMostOnce()).findByIdAndUserId(any(), any());
        verify(financialTransactionModelMapper, atMostOnce()).mapFinancialTransactionEntityToFinancialTransactionDTO(
                any(FinancialTransaction.class));
        verify(financialTransactionCategoryRepository, atMostOnce()).findByIdAndUserId(any(), any());

    }

    @Test
    @DisplayName("Should throw an exception when category type doesn't match the category type")
    void CreateFinancialTransaction_TypeMismatchCategory_ThrowAppRuntimeException() {
        // given
        User user = createUserForTest();

        FinancialTransactionCreateDTO financialTransactionCreateDTO = createFinancialTransactionCreateDTO();

        Wallet wallet = new Wallet();
        when(walletRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.of(wallet));
        FinancialTransaction financialTransactionEntity = createFinancialTransactionEntity(ID_1L);
        when(ftRepository.save(Mockito.any(FinancialTransaction.class))).thenReturn(financialTransactionEntity);
        FinancialTransactionDTO financialTransactionDTO = createFinancialTransactionDTO();
        when(financialTransactionModelMapper.mapFinancialTransactionEntityToFinancialTransactionDTO(
                any(FinancialTransaction.class))).thenReturn(financialTransactionDTO);

        FinancialTransactionCategory financialTransactionCategory = createFinancialTransactionCategory(INCOME, user);
        when(financialTransactionCategoryRepository.findByIdAndUserId(any(), eq(ID_1L))).thenReturn(
                Optional.ofNullable(financialTransactionCategory));

        // when
        AppRuntimeException exception = assertThrows(AppRuntimeException.class, () ->
                financialTransactionService.createFinancialTransaction(financialTransactionCreateDTO, user.getId()));

        // then
        assertEquals(ErrorCode.FT002.getBusinessStatusCode(), exception.getStatusCode());

    }

    @Test
    @DisplayName("Shouldn't create financial transaction without existing wallet and throw an exception")
    void CreateFinancialTransaction_WalletNotExist_ThrowAppRuntimeException() {
        // given
        User user = createUserForTest();

        FinancialTransactionCreateDTO financialTransactionCreateDTO = createFinancialTransactionCreateDTO();

        when(walletRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.empty());

        // when
        AppRuntimeException exception = assertThrows(AppRuntimeException.class, () ->
                financialTransactionService.createFinancialTransaction(financialTransactionCreateDTO, user.getId()));

        // then
        assertEquals(ErrorCode.W001.getBusinessStatusCode(), exception.getStatusCode());
        verify(walletRepository, never()).save(any());

    }

    @Test
    @DisplayName("Should create financial transaction with empty description")
    void CreateFinancialTransaction_EmptyDescription_ReturnFinancialTransactionDTOWithEmptyDescription() {
        // given
        User user = createUserForTest();

        FinancialTransactionCreateDTO financialTransactionCreateDTO = createFinancialTransactionCreateDTO();
        financialTransactionCreateDTO.setDescription(EMPTY);

        Wallet wallet = new Wallet();
        when(walletRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.of(wallet));

        FinancialTransaction financialTransactionEntity = createFinancialTransactionEntity(ID_1L);
        financialTransactionEntity.setDescription(EMPTY);
        when(ftRepository.save(Mockito.any(FinancialTransaction.class))).thenReturn(financialTransactionEntity);

        FinancialTransactionDTO financialTransactionDTO = createFinancialTransactionDTO();
        financialTransactionDTO.setDescription(EMPTY);
        when(financialTransactionModelMapper.mapFinancialTransactionEntityToFinancialTransactionDTO(
                any(FinancialTransaction.class))).thenReturn(financialTransactionDTO);

        FinancialTransactionCategory financialTransactionCategory = createFinancialTransactionCategory(EXPENSE, user);
        when(financialTransactionCategoryRepository.findByIdAndUserId(any(), any())).thenReturn(
                Optional.ofNullable(financialTransactionCategory));

        // when
        FinancialTransactionDTO result = financialTransactionService.createFinancialTransaction(
                financialTransactionCreateDTO, user.getId());

        // then
        Assertions.assertAll(() -> assertEquals(EMPTY, financialTransactionDTO.getDescription()), () -> assertEquals(
                financialTransactionDTO, result), () -> assertEquals(
                financialTransactionDTO.getId(), result.getId()));
        verify(ftRepository, atMostOnce()).save(any(FinancialTransaction.class));
        verify(walletRepository, atMostOnce()).findByIdAndUserId(any(), any());
        verify(financialTransactionModelMapper, atMostOnce()).mapFinancialTransactionEntityToFinancialTransactionDTO(
                any(FinancialTransaction.class));
        verify(financialTransactionCategoryRepository, atMostOnce()).findByIdAndUserId(any(), any());

    }


    public FinancialTransaction createFinancialTransactionEntity(Long financialTransactionId) {
        FinancialTransaction financialTransaction = new FinancialTransaction();
        financialTransaction.setId(financialTransactionId);
        financialTransaction.setType(INCOME);
        financialTransaction.setAmount(TWO);
        financialTransaction.setDate(Instant.parse("2024-01-01T00:00:00Z"));
        return financialTransaction;
    }

    public FinancialTransactionCreateDTO createFinancialTransactionCreateDTO() {
        return new FinancialTransactionCreateDTO(WALLET_ID_1L, ONE, DESCRIPTION, EXPENSE,
                DATE_NOW, CATEGORY_ID);
    }

    public FinancialTransactionDTO createFinancialTransactionDTO() {
        return new FinancialTransactionDTO(ID_1L, ONE, DESCRIPTION, EXPENSE, DATE_NOW, CATEGORY_ID);
    }

    public FinancialTransactionCategory createFinancialTransactionCategory(FinancialTransactionType type, User user) {
        return new FinancialTransactionCategory(ID_1L, "Example Category Name", type, null, DATE_NOW, user);

    }

    public User createUserForTest() {
        return User.builder().id(1L).build();
    }


}