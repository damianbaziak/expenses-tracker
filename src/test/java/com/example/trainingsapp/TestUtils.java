package com.example.trainingsapp;

import com.example.trainingsapp.user.api.model.User;

public class TestUtils {

    private static final Long USER_ID_1L = 1L;

    private static final String USER_EMAIL = "user@example@email.com";

    public static User createUserForTest() {
        return User.builder()
                .id(USER_ID_1L)
                .email(USER_EMAIL)
                .build();
    }
}
