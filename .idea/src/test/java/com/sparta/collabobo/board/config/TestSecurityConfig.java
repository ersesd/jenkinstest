package com.sparta.collabobo.board.config;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class TestSecurityConfig {
    
    @MockBean
    private UserDetailsService userDetailsService;
    
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("testUser")
                               .password("testPassword")
                               .authorities("ROLE_USER")
                               .build();
        return new InMemoryUserDetailsManager(user);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 보호 기능 비활성화
            .authorizeRequests(authz -> authz
                .anyRequest().authenticated()
            );
        return http.build();
    }
}