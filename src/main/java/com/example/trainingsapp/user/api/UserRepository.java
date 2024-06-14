package com.example.trainingsapp.user.api;

import com.example.trainingsapp.user.model.MyUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<MyUser, Long> {
    Optional<MyUser> findByUsername(String username);
    boolean existsByEmail(String email);

}

