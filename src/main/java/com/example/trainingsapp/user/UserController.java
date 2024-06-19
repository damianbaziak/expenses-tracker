package com.example.trainingsapp.user;

import com.example.trainingsapp.user.api.dto.UsernameUpdateDTO;
import com.example.trainingsapp.user.model.MyUser;
import com.example.trainingsapp.user.api.UserServiceImpl;
import com.example.trainingsapp.user.api.dto.EmailUptadeDTO;
import com.example.trainingsapp.user.api.dto.PasswordUptadeDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("api/users")
@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Optional<MyUser> user = userService.getUserById(id);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PatchMapping("update/username/{id}")
    public ResponseEntity uptadeUsername(@PathVariable Long id, @Valid @RequestBody UsernameUpdateDTO usernameUpdateDTO) {
        MyUser updatedUser = userService.updateUsername(id, usernameUpdateDTO);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping("update/email/{id}")
    public ResponseEntity updateEmail(@PathVariable Long id, @Valid @RequestBody EmailUptadeDTO emailUptadeDTO) {
        MyUser updatedUser = userService.updateEmail(id, emailUptadeDTO);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }


    @PatchMapping("update/password/{id}")
    public ResponseEntity updatePassword(@PathVariable Long id, @Valid @RequestBody PasswordUptadeDTO passwordUptadeDto) {
        MyUser updatedUser = userService.updatePassword(id, passwordUptadeDto);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }
}