package com.example.trainingsapp.user.api;

import com.example.trainingsapp.user.api.dto.UsernameUpdateDTO;
import com.example.trainingsapp.user.api.dto.EmailUptadeDTO;
import com.example.trainingsapp.user.api.dto.PasswordUptadeDTO;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/signup")
    public ResponseEntity registerUser(@Valid @RequestBody UserDTO userDTO) {
        Optional<User> userFromDb = userService.getUserByUsername(userDTO.getUsername());

        if (userFromDb.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        return new ResponseEntity<>(userService.addUser(userDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Optional<User> user = userService.getUser(id);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PatchMapping("update/username/{id}")
    public ResponseEntity uptadeUsername(@PathVariable Long id, @Valid @RequestBody UsernameUpdateDTO usernameUpdateDTO) {
        User updatedUser = userService.updateUsername(id, usernameUpdateDTO);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping("update/email/{id}")
    public ResponseEntity updateEmail(@PathVariable Long id, @Valid @RequestBody EmailUptadeDTO emailUptadeDTO) {
        User updatedUser = userService.updateEmail(id, emailUptadeDTO);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }


    @PatchMapping("update/password/{id}")
    public ResponseEntity updatePassword(@PathVariable Long id, @Valid @RequestBody PasswordUptadeDTO passwordUptadeDto) {
        User updatedUser = userService.udatePassword(id, passwordUptadeDto);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }
}