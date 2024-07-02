package com.example.trainingsapp.user.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Valid
public final class UsernameUpdateDTO {

    @NotBlank(message = "username is mandatory")
    @Size(min = 2, max = 15, message = "username must be between 2 and 15 characters")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

