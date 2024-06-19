package com.example.trainingsapp.user.api;

import com.example.trainingsapp.user.api.dto.EmailUptadeDTO;
import com.example.trainingsapp.user.api.dto.PasswordUptadeDTO;
import com.example.trainingsapp.user.api.dto.UsernameUpdateDTO;
import com.example.trainingsapp.user.model.MyUser;

import java.util.Optional;

public interface UserService {
    Optional<MyUser> getUserById(Long id);

    MyUser updateUsername(Long id, UsernameUpdateDTO usernameUpdateDTO);

    MyUser updateEmail(Long id, EmailUptadeDTO emailUptadeDTO);

    MyUser updatePassword(Long id, PasswordUptadeDTO passwordUptadeDto);


}
