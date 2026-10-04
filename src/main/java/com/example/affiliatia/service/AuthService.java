package com.example.affiliatia.service;

import com.example.affiliatia.dto.request.LoginRequest;
import com.example.affiliatia.dto.request.RegisterRequest;
import com.example.affiliatia.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
