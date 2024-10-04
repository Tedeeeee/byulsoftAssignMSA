package com.example.authservice.service;

import com.example.authservice.dto.TokenResponseDto;

public interface AuthService {
    /**
     * 설명 : RefreshToken 을 이용한 새로운 토큰 재발급
     * @since : 2024.10.04
     * @author : T.S YUN
     */
    TokenResponseDto resetRefreshToken(String refreshToken);
}
