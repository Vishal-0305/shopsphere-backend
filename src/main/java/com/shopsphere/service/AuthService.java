package com.shopsphere.service;

import com.shopsphere.dto.LoginRequest;

public interface AuthService {
    String login(LoginRequest request);
}
