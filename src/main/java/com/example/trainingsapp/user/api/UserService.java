package com.example.trainingsapp.user.api;

import com.example.trainingsapp.user.api.dto.EmailUptadeDTO;
import com.example.trainingsapp.user.api.dto.PasswordUptadeDTO;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.api.dto.UsernameUpdateDTO;
import com.example.trainingsapp.user.model.User;

import java.util.Optional;

public interface UserService {

    User addUser(UserDTO userDTO);

    Optional<User> getUser(Long id);

    Optional<User> getUserByUsername(String username);

    User updateUsername(Long id, UsernameUpdateDTO usernameUpdateDTO);

    User updateEmail(Long id, EmailUptadeDTO emailUptadeDTO);

    User udatePassword(Long id, PasswordUptadeDTO passwordUptadeDto);

}
