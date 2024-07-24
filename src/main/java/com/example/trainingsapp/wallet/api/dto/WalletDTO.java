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

    public String getName() {
        return name;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (WalletDTO) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.creationDate, that.creationDate) &&
                Objects.equals(this.userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationDate, userId);
    }

    @Override
    public String toString() {
        return "WalletDTO[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "creationDate=" + creationDate + ", " +
                "userId=" + userId + ']';
    }

}
