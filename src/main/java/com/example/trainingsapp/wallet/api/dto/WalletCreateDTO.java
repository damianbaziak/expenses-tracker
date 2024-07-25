package com.example.trainingsapp.wallet.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class WalletCreateDTO {
    private  @NotBlank @Size(min = 2, max = 20, message =
            "Name should contains maximum 20 characters.") @Pattern(regexp = "[\\w ]+") String name;

    public WalletCreateDTO() {
    }

    public WalletCreateDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (WalletCreateDTO) obj;
        return Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "WalletCreateDTO[" +
                "name=" + name + ']';
    }


}
