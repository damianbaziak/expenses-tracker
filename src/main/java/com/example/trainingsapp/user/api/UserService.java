package com.example.trainingsapp.user.api;

import com.example.trainingsapp.user.api.dto.EmailUptadeDTO;
import com.example.trainingsapp.user.api.dto.PasswordUptadeDTO;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.api.dto.UsernameUpdateDTO;
import com.example.trainingsapp.user.api.model.User;

public interface UserService {
    UserDTO getUserById(Long id);

    User updateUsername(Long id, UsernameUpdateDTO usernameUpdateDTO);

    User updateEmail(Long id, EmailUptadeDTO emailUptadeDTO);

    User updatePassword(Long id, PasswordUptadeDTO passwordUptadeDto);

    User findUserByEmail(String email);


}
