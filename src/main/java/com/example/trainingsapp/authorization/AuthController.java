package com.example.trainingsapp.authorization;

import com.example.trainingsapp.authorization.api.AuthService;
import com.example.trainingsapp.authorization.api.dto.UserLoginDTO;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>("User created correctly", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        return new ResponseEntity<>(authService.loginUser(userLoginDTO), HttpStatus.OK);
    }
}
