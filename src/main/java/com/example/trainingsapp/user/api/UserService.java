package com.example.trainingsapp.user.api;

import com.example.trainingsapp.user.api.dto.EmailUptadeDTO;
import com.example.trainingsapp.user.api.dto.PasswordUptadeDTO;
import com.example.trainingsapp.user.api.dto.UsernameUpdateDTO;
import com.example.trainingsapp.user.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);

    User updateUsername(Long id, UsernameUpdateDTO usernameUpdateDTO);

    User updateEmail(Long id, EmailUptadeDTO emailUptadeDTO);

    User updatePassword(Long id, PasswordUptadeDTO passwordUptadeDto);


}
