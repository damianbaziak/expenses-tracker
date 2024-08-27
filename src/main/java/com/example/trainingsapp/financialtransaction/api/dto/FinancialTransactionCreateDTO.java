package com.example.trainingsapp.financialtransaction.api.dto;

import com.example.trainingsapp.financialtransaction.api.model.FinancialTransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class FinancialTransactionCreateDTO {

    @Min(1)
    @NotNull
    Long walletId;

    @Size(max = 255)
    private String description;

    @Digits(integer = 12, fraction = 2)
    @PositiveOrZero
    private BigDecimal amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant date;

    @NotNull
    private FinancialTransactionType type;

    private Long categoryId;

    public FinancialTransactionCreateDTO(Long walletId, String description, BigDecimal amount, Instant date, FinancialTransactionType type, Long categoryId) {
        this.walletId = walletId;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.categoryId = categoryId;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public FinancialTransactionType getType() {
        return type;
    }

    public void setType(FinancialTransactionType type) {
        this.type = type;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinancialTransactionCreateDTO that = (FinancialTransactionCreateDTO) o;
        return Objects.equals(walletId, that.walletId) && Objects.equals(description, that.description) && Objects.equals(
                amount, that.amount) && Objects.equals(date, that.date) && type == that.type && Objects.equals(
                categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(walletId, description, amount, date, type, categoryId);
    }
}
