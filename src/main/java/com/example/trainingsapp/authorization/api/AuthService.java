package com.example.trainingsapp.authorization.api;

import com.example.trainingsapp.authorization.api.dto.UserLoginDTO;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.model.MyUser;

import java.util.Optional;

public interface AuthService {

    MyUser addUser(UserDTO userDTO);

    Optional<MyUser> findById(Long id);

    Optional<MyUser> getUserByUsername(String username);

    String loginUser(UserLoginDTO userLoginDTO);


}
