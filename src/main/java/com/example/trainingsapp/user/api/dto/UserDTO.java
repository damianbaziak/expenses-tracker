package com.example.trainingsapp.user.api.dto;

import com.example.trainingsapp.wallets.model.Wallet;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Valid
public class UserDTO {

    @NotBlank(message = "firstname is mandatory")
    @Size(min = 2, message = "firstname must have min 2 characters")
    private String firstname;

    @NotBlank(message = "lastname is mandatory")
    @Size(min = 2, message = "lastname must have min 2 characters")
    private String lastname;

    @Min(16)
    @Max(60)
    private int age;

    @NotNull
    @Email(message = "invalid email adress")
    private String email;

    @NotBlank(message = "username is mandatory")
    @Size(min = 2, max = 15, message = "username must be between 2 and 15 characters")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9.]{10}$", message = "Password muss contain exactly 10 characters and can contain only letters and digits")
    @NotBlank(message = "invalid password")
    private String password;

    private List<Wallet> wallets = new ArrayList<>();

    public UserDTO(String firstname, String lastname, int age, String email, String username, String password, List<Wallet> wallets) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.email = email;
        this.username = username;
        this.password = password;
        this.wallets = wallets;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Wallet> getWallets() {
        return wallets;
    }

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }
}



