package com.example.trainingsapp.user;

import com.example.trainingsapp.user.api.UserService;
import com.example.trainingsapp.user.api.dto.UserEmailUptadeDTO;
import com.example.trainingsapp.user.api.dto.UserPasswordUpdateDTO;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.api.dto.UserUsernameUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {
        UserDTO user = userService.findUserById(id);

        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/username")
    public ResponseEntity uptadeUsername(@PathVariable Long id, @Valid @RequestBody UserUsernameUpdateDTO usernameUpdateDTO) {
        UserDTO updatedUser = userService.updateUsername(id, usernameUpdateDTO);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity updateEmail(@PathVariable Long id, @Valid @RequestBody UserEmailUptadeDTO emailUpdateDTO) {
        UserDTO updatedUser = userService.updateEmail(id, emailUpdateDTO);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }


    @PatchMapping("/{id}/password")
    public ResponseEntity updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordUpdateDTO passwordUptadeDto) {
        UserDTO updatedUser = userService.updatePassword(id, passwordUptadeDto);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }
}