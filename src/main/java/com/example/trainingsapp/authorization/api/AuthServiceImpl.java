package com.example.trainingsapp.authorization.api;

import com.example.trainingsapp.authorization.MyUserDetailsService;
import com.example.trainingsapp.authorization.api.dto.UserLoginDTO;
import com.example.trainingsapp.authorization.webtoken.JwtService;
import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Override
    public MyUser addUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new AppRuntimeException(ErrorCode.U001, "User with this email already exist");
        }
        MyUser user = MyUser.builder()
                .firstname(userDTO.getFirstname())
                .lastname(userDTO.getLastname())
                .age(userDTO.getAge())
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(hashedPassword(userDTO.getPassword()))
                .build();
        return userRepository.save(user);
    }

    public String loginUser(UserLoginDTO userLoginDTO) {
        Optional<MyUser> userFromDb = userRepository.findByemail(userLoginDTO.email());

        if (userFromDb.isEmpty()) {
            throw new UsernameNotFoundException("User with this email or password not exist");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginDTO.email(), userLoginDTO.password()
        ));
        return jwtService.generateToken(userDetailsService.loadUserByUsername(userLoginDTO.email()));
    }

    @Override
    public Optional<MyUser> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<MyUser> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public String hashedPassword(String password) {
        return passwordEncoder.encode(password);
    }


}
