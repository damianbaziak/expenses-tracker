package com.example.trainingsapp.user.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Valid
public class PasswordUptadeDTO {

    @Pattern(regexp = "^[a-zA-Z0-9.]{10}$", message = "Password muss contain exactly 10 characters and can contain only letters and digits")
    @NotBlank(message = "invalid password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
