package com.example.trainingsapp.wallet.api.dto;

import java.time.Instant;

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
}
