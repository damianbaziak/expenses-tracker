package com.example.trainingsapp.user.controller;

import com.example.trainingsapp.user.dto.UserDTO;
import com.example.trainingsapp.user.entity.User;
import com.example.trainingsapp.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity registerUser(@Valid @RequestBody UserDTO userDTO) {
        Optional<User> userFromDb = userService.getUserbyUsername(userDTO.getUsername());

        if (userFromDb.isPresent()) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
            }
        return new ResponseEntity<>(userService.addUser(userDTO), HttpStatus.CREATED);
    }
}
