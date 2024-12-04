package com.example.trainingsapp.user.api;

import com.example.trainingsapp.user.api.dto.UserEmailUptadeDTO;
import com.example.trainingsapp.user.api.dto.UserPasswordUpdateDTO;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.api.dto.UserUsernameUpdateDTO;
import com.example.trainingsapp.user.api.model.User;

public interface UserService {
    UserDTO findUserById(Long id);

    UserDTO updateUsername(Long id, UserUsernameUpdateDTO usernameUpdateDTO);

    UserDTO updateEmail(Long id, UserEmailUptadeDTO emailUptadeDTO);

    UserDTO updatePassword(Long id, UserPasswordUpdateDTO passwordUptadeDto);

    User findUserByEmail(String email);


}
