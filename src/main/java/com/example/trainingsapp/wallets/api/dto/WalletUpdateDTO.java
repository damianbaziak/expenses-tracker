package com.example.trainingsapp.wallets.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class WalletUpdateDTO {
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
