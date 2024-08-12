package com.example.trainingsapp.user.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class EmailUptadeDTO {
    @Email
    @NotNull
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
