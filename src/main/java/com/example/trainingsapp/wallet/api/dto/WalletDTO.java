package com.example.trainingsapp.wallet.api.dto;

import java.time.Instant;
import java.util.Objects;

public final class WalletDTO {
    private final Long id;
    private final String name;
    private final Instant creationDate;
    private final Long userId;

    public WalletDTO(Long id, String name, Instant creationDate, Long userId) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.userId = userId;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Instant creationDate() {
        return creationDate;
    }

    public Long userId() {
        return userId;
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
