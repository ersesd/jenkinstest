package com.sparta.collabobo.user.service;


import com.sparta.collabobo.entity.User;
import com.sparta.collabobo.entity.UserRoleEnum;
import com.sparta.collabobo.jwt.JwtUtil;
import com.sparta.collabobo.user.dto.request.LoginRequestDto;
import com.sparta.collabobo.user.dto.request.SignupRequestDto;
import com.sparta.collabobo.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  // ADMIN_TOKEN

  private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

  public void signup(SignupRequestDto requestDto) {
    String username = requestDto.getUsername();
    String password = passwordEncoder.encode(requestDto.getPassword());

    // 회원 중복 확인
    Optional<User> checkUsername = userRepository.findByUsername(username);
    if (checkUsername.isPresent()) {
      throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
    }

    // email 중복확인
    String email = requestDto.getEmail();
    Optional<User> checkEmail = userRepository.findByEmail(email);
    if (checkEmail.isPresent()) {
      throw new IllegalArgumentException("중복된 Email 입니다.");
    }

    String nickname = requestDto.getNickname();
    Optional<User> checkNickname = userRepository.findByNickname(nickname);
    if (checkNickname.isPresent()) {
      throw new IllegalArgumentException("중복된 Nickname 입니다.");
    }

    // 사용자 ROLE 확인
    UserRoleEnum role = UserRoleEnum.USER;
    if (requestDto.isAdmin()) {
      if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
        throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
      }
      role = UserRoleEnum.ADMIN;
    }

    // 사용자 등록
    User user = new User(requestDto, password, role);
    userRepository.save(user);
  }


}