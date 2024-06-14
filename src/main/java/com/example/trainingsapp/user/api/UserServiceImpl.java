package com.example.trainingsapp.user.api;

import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.dto.EmailUptadeDTO;
import com.example.trainingsapp.user.api.dto.PasswordUptadeDTO;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.api.dto.UsernameUpdateDTO;
import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.user.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

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
                .password(userDTO.getPassword())
                .build();
        return userRepository.save(user);
    }

    @Override
    public Optional<MyUser> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<MyUser> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public MyUser updateUsername(Long id, UsernameUpdateDTO usernameUpdateDTO) {
        Optional<MyUser> userFromDb = userRepository.findById(id);
        if (!userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this id not found.");
        }
        MyUser existingUser = userFromDb.get();

        if (usernameUpdateDTO.getUsername() != null) {
            existingUser.setUsername(usernameUpdateDTO.getUsername());
        }

        return userRepository.save(existingUser);

    }

    @Override
    public MyUser udatePassword(Long id, PasswordUptadeDTO passwordUptadeDto) {
        Optional<MyUser> userFromDb = userRepository.findById(id);
        if (!userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this id not found.");
        }
        MyUser existingUser = userFromDb.get();

        if (passwordUptadeDto.getPassword() != null) {
            existingUser.setPassword(passwordUptadeDto.getPassword());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public MyUser updateEmail(Long id, EmailUptadeDTO emailUptadeDTO) {
        Optional<MyUser> userFromDb = userRepository.findById(id);
        if (!userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this id not found.");
        }
        MyUser existingUser = userFromDb.get();

        if (emailUptadeDTO.getEmail() != null) {
            existingUser.setEmail(emailUptadeDTO.getEmail());
        }

        return userRepository.save(existingUser);
    }
}




