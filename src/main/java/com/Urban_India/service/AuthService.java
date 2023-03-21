package com.Urban_India.service;

import com.Urban_India.payload.LoginDto;
import com.Urban_India.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
