package com.example.trainingsapp.user.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Valid
public class UserDTO {

    @NotNull(message = "firstname shouldn't be null")
    @NotBlank(message = "firstname is mandatory")
    @Size(min = 2, message = "firstname must have min 2 characters")
    private String firstname;

    //@NotNull(message = "firstname shouldn't be null")
    @NotBlank(message = "lastname is mandatory")
    @Size(min = 2, message = "lastname must have min 2 characters")
    private String lastname;

    @Min(16)
    @Max(60)
    private int age;

    @NotNull
    @Email
    private String email;

    @NotBlank(message = "username is mandatory")
    @Size(min = 2, max = 15, message = "username must be between 2 and 20 characters")
    private String username;

    @Pattern(regexp = "\\d{10}")
    @NotBlank(message = "password is mandatory")
    private String password;

}



