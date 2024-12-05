package com.example.trainingsapp.user.api;

import com.example.trainingsapp.user.api.dto.UserEmailUpdateDTO;
import com.example.trainingsapp.user.api.dto.UserPasswordUpdateDTO;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.api.dto.UserUsernameUpdateDTO;
import com.example.trainingsapp.user.api.model.User;

public interface UserService {
    UserDTO findUserById(Long id, Long principalUserId);

    UserDTO updateUsername(Long id, UserUsernameUpdateDTO updateDTO, Long principalUserId);

    UserDTO updateEmail(Long id, UserEmailUpdateDTO updateDTO, Long principalUserId);

    UserDTO updatePassword(Long id, UserPasswordUpdateDTO updateDTO, Long principalUserId);

    User findUserByEmail(String email);


}
