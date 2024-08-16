package com.example.trainingsapp.user.api.impl;

import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.UserService;
import com.example.trainingsapp.user.api.dto.EmailUptadeDTO;
import com.example.trainingsapp.user.api.dto.PasswordUptadeDTO;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.api.dto.UsernameUpdateDTO;
import com.example.trainingsapp.user.model.User;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new AppRuntimeException(ErrorCode.U003, "User with this id does not exist");
        }
        User existingUser = user.get();

        UserDTO userDTO = new UserDTO(existingUser.getFirstname(), existingUser.getLastname(), existingUser.getAge(),
                existingUser.getEmail(), existingUser.getUsername(), existingUser.getPassword());
        return userDTO;
    }

    @Override
    public User updateUsername(Long id, UsernameUpdateDTO usernameUpdateDTO) {
        Optional<User> userFromDb = userRepository.findById(id);
        if (!userFromDb.isPresent()) {
            throw new AppRuntimeException(ErrorCode.U003, "User with this id not exist");
        }
        User existingUser = userFromDb.get();

        if (usernameUpdateDTO.getUsername() != null) {
            existingUser.setUsername(usernameUpdateDTO.getUsername());
        }

        return userRepository.save(existingUser);

    }

    @Override
    public User updatePassword(Long id, PasswordUptadeDTO passwordUptadeDto) {
        Optional<User> userFromDb = userRepository.findById(id);
        if (!userFromDb.isPresent()) {
            throw new AppRuntimeException(ErrorCode.U003, "User with this id not exist");
        }
        User existingUser = userFromDb.get();

        if (passwordUptadeDto.getPassword() != null) {
            existingUser.setPassword(passwordUptadeDto.getPassword());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public User updateEmail(Long id, EmailUptadeDTO emailUptadeDTO) {
        Optional<User> userFromDb = userRepository.findById(id);
        if (!userFromDb.isPresent()) {
            throw new AppRuntimeException(ErrorCode.U003, "User with this id not exist");
        }
        User existingUser = userFromDb.get();

        if (emailUptadeDTO.getEmail() != null) {
            existingUser.setEmail(emailUptadeDTO.getEmail());
        }

        return userRepository.save(existingUser);
    }
}




