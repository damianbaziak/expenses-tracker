package com.example.trainingsapp.user;

import com.example.trainingsapp.user.api.UserService;
import com.example.trainingsapp.user.api.dto.EmailUptadeDTO;
import com.example.trainingsapp.user.api.dto.PasswordUptadeDTO;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.api.dto.UsernameUpdateDTO;
import com.example.trainingsapp.user.api.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);

        return ResponseEntity.ok(user);
    }

    @PatchMapping("/update/username/{id}")
    public ResponseEntity uptadeUsername(@PathVariable Long id, @Valid @RequestBody UsernameUpdateDTO usernameUpdateDTO) {
        User updatedUser = userService.updateUsername(id, usernameUpdateDTO);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping("/update/email/{id}")
    public ResponseEntity updateEmail(@PathVariable Long id, @Valid @RequestBody EmailUptadeDTO emailUptadeDTO) {
        User updatedUser = userService.updateEmail(id, emailUptadeDTO);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }


    @PatchMapping("/update/password/{id}")
    public ResponseEntity updatePassword(@PathVariable Long id, @Valid @RequestBody PasswordUptadeDTO passwordUptadeDto) {
        User updatedUser = userService.updatePassword(id, passwordUptadeDto);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }
}