package com.sparta.collabobo.board.config;

import java.util.Collections;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import com.sparta.collabobo.entity.User;

@TestConfiguration
public class TestConfig {
    @Bean
    public UserDetails testUserDetails() {
        return new org.springframework.security.core.userdetails.User("testUsername", "testPassword", Collections.emptyList());
    }

    @Bean
    public User testUser() {
        User user = new User();
        user.setNickname("testNickname");
        user.setPassword("testPassword");
        // Add more properties as needed
        return user;
    }
}