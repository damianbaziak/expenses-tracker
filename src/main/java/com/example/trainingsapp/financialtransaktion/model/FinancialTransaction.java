package com.example.trainingsapp.financialtransaktion.model;

import com.example.trainingsapp.financialtransaktioncategory.model.FinancialTransactionCategory;
import com.example.trainingsapp.wallet.model.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "financial_transactions")
public class FinancialTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @Column(name = "transaction_type", columnDefinition = "ENUM('INCOME', 'EXPENSE')")
    private FinancialTransactionType type;

    @DecimalMin("0,0")
    private BigDecimal amount;

    @Column(name = "transaction_date")
    private Instant date;

    @ManyToOne(fetch = FetchType.LAZY)
    private FinancialTransactionCategory financialTransactionCategory;

    @Size(max = 255)
    private String description;

    public FinancialTransaction() {
    }

    public FinancialTransaction(Long id, Wallet wallet, FinancialTransactionType type, BigDecimal amount, Instant date,
                                FinancialTransactionCategory financialTransactionCategory, String description) {
        this.id = id;
        this.wallet = wallet;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.financialTransactionCategory = financialTransactionCategory;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public FinancialTransactionType getType() {
        return type;
    }

    public void setType(FinancialTransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public FinancialTransactionCategory getFinancialTransactionCategory() {
        return financialTransactionCategory;
    }

    public void setFinancialTransactionCategory(FinancialTransactionCategory financialTransactionCategory) {
        this.financialTransactionCategory = financialTransactionCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


