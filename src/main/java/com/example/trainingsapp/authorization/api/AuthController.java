package com.example.trainingsapp.authorization.api;

import com.example.trainingsapp.authorization.api.dto.UserLoginDTO;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.model.MyUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Validated
@RequestMapping("api/auth")
@RestController
public class AuthController {

    @Autowired
    AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody UserDTO userDTO) {
        Optional<MyUser> userFromDb = authService.getUserByUsername(userDTO.getUsername());

        if (userFromDb.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("User with this username already extists");
        }
        return new ResponseEntity<>(authService.addUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        return new ResponseEntity<>(authService.loginUser(userLoginDTO), HttpStatus.OK);
    }
}
