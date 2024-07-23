package com.example.trainingsapp.wallet.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public record WalletCreateDTO(
        @NotBlank @Size(min = 2, max = 20, message = "Name should contains maximum 20 characters.") @Pattern(regexp = "[\\w ]+") String name) {


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (WalletCreateDTO) obj;
        return Objects.equals(this.name, that.name);
    }

    @Override
    public String toString() {
        return "WalletCreateDTO[" +
                "name=" + name + ']';
    }

}
