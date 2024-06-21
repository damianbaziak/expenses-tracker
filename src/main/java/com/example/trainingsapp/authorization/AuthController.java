package com.example.trainingsapp.authorization;

import com.example.trainingsapp.authorization.api.AuthServiceImpl;
import com.example.trainingsapp.authorization.api.dto.UserLoginDTO;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.model.MyUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Validated
@RequestMapping("api/auth")
@RestController
public class AuthController {

    @Autowired
    AuthServiceImpl authService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(authService.addUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        return new ResponseEntity<>(authService.loginUser(userLoginDTO), HttpStatus.OK);
    }
}
