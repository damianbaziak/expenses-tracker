package com.example.trainingsapp.user.api.impl;

import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.UserService;
import com.example.trainingsapp.user.api.dto.EmailUptadeDTO;
import com.example.trainingsapp.user.api.dto.PasswordUptadeDTO;
import com.example.trainingsapp.user.api.dto.UsernameUpdateDTO;
import com.example.trainingsapp.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }


    @Override
    public User updateUsername(Long id, UsernameUpdateDTO usernameUpdateDTO) {
        Optional<User> userFromDb = userRepository.findById(id);
        if (!userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this id not found.");
        }
        User existingUser = userFromDb.get();

        if (usernameUpdateDTO.getUsername() != null) {
            existingUser.setUsername(usernameUpdateDTO.getUsername());
        }

        return userRepository.save(existingUser);

    }

    @Override
    public User updatePassword(Long id, PasswordUptadeDTO passwordUptadeDto) {
        Optional<User> userFromDb = userRepository.findById(id);
        if (!userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this id not found.");
        }
        User existingUser = userFromDb.get();

        if (passwordUptadeDto.getPassword() != null) {
            existingUser.setPassword(passwordUptadeDto.getPassword());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public User updateEmail(Long id, EmailUptadeDTO emailUptadeDTO) {
        Optional<User> userFromDb = userRepository.findById(id);
        if (!userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this id not found.");
        }
        User existingUser = userFromDb.get();

        if (emailUptadeDTO.getEmail() != null) {
            existingUser.setEmail(emailUptadeDTO.getEmail());
        }

        return userRepository.save(existingUser);
    }
}




