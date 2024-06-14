package com.example.trainingsapp.user.api;

import com.example.trainingsapp.user.api.dto.EmailUptadeDTO;
import com.example.trainingsapp.user.api.dto.PasswordUptadeDTO;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.api.dto.UsernameUpdateDTO;
import com.example.trainingsapp.user.model.MyUser;

import java.util.Optional;

public interface UserService {

    MyUser addUser(UserDTO userDTO);

    Optional<MyUser> getUserById(Long id);

    Optional<MyUser> getUserByUsername(String username);

    MyUser updateUsername(Long id, UsernameUpdateDTO usernameUpdateDTO);

    MyUser updateEmail(Long id, EmailUptadeDTO emailUptadeDTO);

    MyUser udatePassword(Long id, PasswordUptadeDTO passwordUptadeDto);

}
