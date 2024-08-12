package com.example.trainingsapp.authorization.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UserLoginDTO {
    private @Email @NotNull String email;
    private @Pattern(regexp = "^[a-zA-Z0-9.]{10}$", message = "Password muss contain exactly 10 characters and can contain only letters and digits") @NotBlank(message = "invalid password") String password;

    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

}
