package com.example.trainingsapp.user.service;

import com.example.trainingsapp.user.dto.UserDTO;
import com.example.trainingsapp.user.entity.User;
import com.example.trainingsapp.user.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User addUser(UserDTO userDTO) {
        User user = User
                .build(null, userDTO.getFirstname(), userDTO.getLastname(), userDTO.getAge(), userDTO.getEmail(),
                        userDTO.getUsername(), userDTO.getPassword());
        return userRepository.save(user);

    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateUser(@NotNull Long id, UserDTO userDTO) {
        Optional<User> userFromDb = getUser(id);
        if (!userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this id not found.");
        }
        User existingUser = userFromDb.get();
        existingUser.setFirstname(userDTO.getFirstname());
        existingUser.setLastname(userDTO.getLastname());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setAge(userDTO.getAge());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setUsername(userDTO.getUsername());

        return userRepository.save(existingUser);
    }
}




