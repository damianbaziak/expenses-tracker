package com.example.trainingsapp.user.api;

import com.example.trainingsapp.user.api.dto.EmailUptadeDTO;
import com.example.trainingsapp.user.api.dto.PasswordUptadeDTO;
import com.example.trainingsapp.user.api.dto.UserDTO;
import com.example.trainingsapp.user.api.dto.UsernameUpdateDTO;
import com.example.trainingsapp.user.model.User;
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
    public User addUser(UserDTO userDTO) {
        User user = User
                .build(null, userDTO.getFirstname(), userDTO.getLastname(), userDTO.getAge(), userDTO.getEmail(),
                        userDTO.getUsername(), userDTO.getPassword());
        return userRepository.save(user);

    }

    @Override
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User updateUsername(Long id, UsernameUpdateDTO usernameUpdateDTO) {
        Optional<User> userFromDb = userRepository.findById(id);
        if (!userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this id not found.");
        }
        User existingUser = userFromDb.get();

        if (usernameUpdateDTO.getUsername() != null) {
            existingUser.setUsername(usernameUpdateDTO.getUsername());
        }

        return userRepository.save(existingUser);

    }

    @Override
    public User udatePassword(Long id, PasswordUptadeDTO passwordUptadeDto) {
        Optional<User> userFromDb = userRepository.findById(id);
        if (!userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this id not found.");
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this id not found.");
        }
        User existingUser = userFromDb.get();

        if (emailUptadeDTO.getEmail() != null) {
            existingUser.setEmail(emailUptadeDTO.getEmail());
        }

        return userRepository.save(existingUser);
    }
}




