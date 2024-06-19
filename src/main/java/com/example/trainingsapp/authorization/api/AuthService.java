package com.example.trainingsapp.authorization.api;

import com.example.trainingsapp.authorization.api.dto.UserLoginDTO;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.model.MyUser;

import java.util.Optional;

public interface AuthService {

    public MyUser addUser(UserDTO userDTO);

    public Optional<MyUser> findById(Long id);

    public Optional<MyUser> getUserByUsername(String username);

    public String loginUser(UserLoginDTO userLoginDTO);

}
