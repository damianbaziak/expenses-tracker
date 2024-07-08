package com.example.trainingsapp.wallet.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public final class WalletUpdateDTO {
    private final @NotBlank @Size(min = 2, max = 20, message = "Name should contains maximum 20 characters.") @Pattern(regexp = "[\\w ]+") String name;

    public WalletUpdateDTO(
            @NotBlank @Size(min = 2, max = 20, message = "Name should contains maximum 20 characters.")
            @Pattern(regexp = "[\\w ]+") String name) {
        this.name = name;
    }

    public @NotBlank @Size(min = 2, max = 20, message = "Name should contains maximum 20 characters.") @Pattern(regexp = "[\\w ]+") String name() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (WalletUpdateDTO) obj;
        return Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "WalletUpdateDTO[" +
                "name=" + name + ']';
    }


}



