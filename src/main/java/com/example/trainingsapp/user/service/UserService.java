package com.example.trainingsapp.user.service;

import com.example.trainingsapp.user.dto.UserDTO;
import com.example.trainingsapp.user.entity.User;
import com.example.trainingsapp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User addUser(UserDTO userDTO) {
        User user = User
                .build(0, userDTO.getFirstname(), userDTO.getLastname(), userDTO.getAge(), userDTO.getEmail(),
                        userDTO.getUsername(), userDTO.getPassword());

        return userRepository.save(user);
    }

    public Optional<User> getUser(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserbyUsername(String username) {
        return userRepository.findByUsername(username);
    }


}

