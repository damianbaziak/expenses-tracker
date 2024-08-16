package com.example.trainingsapp.financialtransaktion.api.dto;

import com.example.trainingsapp.financialtransaktion.model.FinancialTransactionType;
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
    private BigDecimal amout;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant date;

    @NotNull
    private FinancialTransactionType type;

    private Long categoryId;

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

    public BigDecimal getAmout() {
        return amout;
    }

    public void setAmout(BigDecimal amout) {
        this.amout = amout;
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
                amout, that.amout) && Objects.equals(date, that.date) && type == that.type && Objects.equals(
                categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(walletId, description, amout, date, type, categoryId);
    }
}
