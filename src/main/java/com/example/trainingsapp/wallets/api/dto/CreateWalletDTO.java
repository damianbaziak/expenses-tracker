package com.example.trainingsapp.wallets.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateWalletDTO {
    @Valid

    @NotBlank
    @Size(min = 2, max = 20, message = "Name should contains maximum 20 characters.")
    @Pattern(regexp = "[\\w ]+")
    private String name;

    public String getName() {
        return name;
    }
}
