package com.example.trainingsapp.user.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class UsernameUpdateDTO {

    @NotBlank(message = "username is mandatory")
    @Size(min = 2, max = 15, message = "username must be between 2 and 15 characters")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsernameUpdateDTO that = (UsernameUpdateDTO) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "UsernameUpdateDTO{" +
                "username='" + username + '\'' +
                '}';
    }
}

