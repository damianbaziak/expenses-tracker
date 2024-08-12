package com.example.trainingsapp.wallet.api.dto;

import java.time.Instant;
import java.util.Objects;

public class WalletDTO {
    private Long id;
    private String name;
    private Instant creationDate;
    private Long userId;

    public WalletDTO() {
    }

    public WalletDTO(Long id, String name, Instant creationDate, Long userId) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalletDTO walletDTO = (WalletDTO) o;
        return Objects.equals(id, walletDTO.id) && Objects.equals(name, walletDTO.name) && Objects.equals(creationDate, walletDTO.creationDate) && Objects.equals(userId, walletDTO.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationDate, userId);
    }
}
