package com.example.trainingsapp.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserUpdateDTO {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9.]{10}$")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

