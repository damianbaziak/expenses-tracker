package com.example.trainingsapp.wallets.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Valid
public final class WalletUpdateDTO {
    @NotBlank
    @Size(min = 2, max = 20, message = "Name should contains maximum 20 characters.")
    @Pattern(regexp = "[\\w ]+")
    private String name;

    public WalletUpdateDTO(String name) {
        this.name = name;
    }

    public WalletUpdateDTO(){
    }

    public String name() {
        return name;
    }

}


