package com.example.trainingsapp.authorization;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {

    @GetMapping("/home")
    public String handleWelcome() {
        return "home";
    }

    @GetMapping("/admin/home")
    public String handleAdminHome() {
        return "home_admin";
    }

    @GetMapping("/user")
    public String handleUser() {
        return "home_user";
    }

    @GetMapping("/login")
    public String handleLogin() {
        return "custom_login";
    }
}

