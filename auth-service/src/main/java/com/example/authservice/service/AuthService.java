package com.example.authservice.service;

import com.example.authservice.dto.AuthMemberDto;
import com.example.authservice.dto.TokenResponseDto;

public interface AuthService {
    /**
     * 설명 : RefreshToken 을 이용한 새로운 토큰 재발급
     * @since : 2024.10.04
     * @author : T.S YUN
     */
    TokenResponseDto resetRefreshToken(String refreshToken);

    /**
     * 설명 : AccessToken을 이용한 로그아웃
     * @author : T.S YUN
     * @since : 2024.10.04
     */
    void logout(String refreshToken);

    /**
     * 설명 : 사용자 회원가입 후 데이터 저장
     * @since : 2024.10.21
     * @author : T.S YUN
     */
    void saveMember(AuthMemberDto authMemberDto);
}
