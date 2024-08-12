package com.example.trainingsapp.wallet.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class WalletUpdateDTO {
    private @NotBlank @Size(min = 2, max = 20, message =
            "Name should contains maximum 20 characters.") @Pattern(regexp = "[\\w ]+") String name;

    public WalletUpdateDTO() {
    }

    public WalletUpdateDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WalletUpdateDTO that = (WalletUpdateDTO) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}



