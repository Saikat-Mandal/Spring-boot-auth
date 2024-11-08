package com.youtube.jwt.service;

import com.youtube.jwt.dto.LoginDto;
import com.youtube.jwt.dto.RegisterDto;
import com.youtube.jwt.entity.User;
import com.youtube.jwt.response.JwtAuthResponse;

public interface AuthService {
    JwtAuthResponse login(LoginDto loginDto);
    User register(RegisterDto registerDto);
}
