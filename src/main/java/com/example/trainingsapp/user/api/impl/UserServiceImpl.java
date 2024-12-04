package com.example.trainingsapp.user.api.impl;

import com.example.trainingsapp.general.exception.AppRuntimeException;
import com.example.trainingsapp.general.exception.ErrorCode;
import com.example.trainingsapp.user.api.UserRepository;
import com.example.trainingsapp.user.api.UserService;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.api.dto.UserEmailUptadeDTO;
import com.example.trainingsapp.user.api.dto.UserPasswordUpdateDTO;
import com.example.trainingsapp.user.api.dto.UserUsernameUpdateDTO;
import com.example.trainingsapp.user.api.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new AppRuntimeException(ErrorCode.U003, "User with this id does not exist");
        }
        User existingUser = user.get();

        return new UserDTO(existingUser.getFirstname(), existingUser.getLastname(), existingUser.getAge(),
                existingUser.getEmail(), existingUser.getUsername(), existingUser.getPassword());
    }

    @Override
    @Transactional
    public UserDTO updateUsername(Long id, UserUsernameUpdateDTO usernameUpdateDTO) {
        Optional<User> userFromDb = userRepository.findById(id);
        if (userFromDb.isEmpty()) {
            throw new AppRuntimeException(ErrorCode.U003, "User with this id not exist");
        }
        User existingUser = userFromDb.get();

        if (usernameUpdateDTO.getUsername() != null) {
            existingUser.setUsername(usernameUpdateDTO.getUsername());
        }
        userRepository.save(existingUser);

        return new UserDTO(existingUser.getFirstname(), existingUser.getLastname(), existingUser.getAge(),
                existingUser.getEmail(), existingUser.getUsername(), existingUser.getPassword());

    }

    @Override
    @Transactional
    public UserDTO updatePassword(Long id, UserPasswordUpdateDTO passwordUptadeDto) {
        Optional<User> userFromDb = userRepository.findById(id);
        if (userFromDb.isEmpty()) {
            throw new AppRuntimeException(ErrorCode.U003, "User with this id not exist");
        }
        User existingUser = userFromDb.get();

        if (passwordUptadeDto.getPassword() != null) {
            existingUser.setPassword(passwordUptadeDto.getPassword());
        }
        userRepository.save(existingUser);

        return new UserDTO(existingUser.getFirstname(), existingUser.getLastname(), existingUser.getAge(),
                existingUser.getEmail(), existingUser.getUsername(), existingUser.getPassword());

    }

    @Override
    @Transactional
    public UserDTO updateEmail(Long id, UserEmailUptadeDTO emailUptadeDTO) {
        Optional<User> userFromDb = userRepository.findById(id);
        if (userFromDb.isEmpty()) {
            throw new AppRuntimeException(ErrorCode.U003, "User with this id not exist");
        }
        User existingUser = userFromDb.get();

        if (emailUptadeDTO.getEmail() != null) {
            existingUser.setEmail(emailUptadeDTO.getEmail());
        }
        userRepository.save(existingUser);

        return new UserDTO(existingUser.getFirstname(), existingUser.getLastname(), existingUser.getAge(),
                existingUser.getEmail(), existingUser.getUsername(), existingUser.getPassword());

    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new AppRuntimeException(ErrorCode.U003,
                String.format("User with this email: %s doesn't exist", email)));
    }
}




