package com.example.authservice.service;

import java.util.Map;

public interface AuthService {
    Map<String, String> resetRefreshToken(String refreshToken);
}
