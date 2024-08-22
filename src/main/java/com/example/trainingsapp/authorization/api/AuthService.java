package com.example.trainingsapp.authorization.api;

import com.example.trainingsapp.authorization.api.dto.UserLoginDTO;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.api.model.User;

public interface AuthService {

    User registerUser(UserDTO userDTO);

    String loginUser(UserLoginDTO userLoginDTO);


}
