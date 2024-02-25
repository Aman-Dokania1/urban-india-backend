package com.Urban_India.service;

import com.Urban_India.payload.IdTokenRequestDto;
import com.Urban_India.payload.LoginDto;
import com.Urban_India.payload.RegisterDto;
import org.modelmapper.internal.Pair;

public interface AuthService {
    String login(LoginDto loginDto);

    Pair<Boolean, String> loginOAuth(IdTokenRequestDto idTokenRequest);
    String register(RegisterDto registerDto);
}
