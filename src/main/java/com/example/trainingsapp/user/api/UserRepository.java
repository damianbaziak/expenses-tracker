package com.example.trainingsapp.user.api;

import com.example.trainingsapp.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

}

