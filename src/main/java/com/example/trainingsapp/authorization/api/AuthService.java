package com.example.trainingsapp.authorization.api;

import com.example.trainingsapp.authorization.api.dto.UserLoginDTO;
import com.example.trainingsapp.user.api.dto.UserDTO;

public interface AuthService {

    void registerUser(UserDTO userDTO);

    String loginUser(UserLoginDTO userLoginDTO);


}
