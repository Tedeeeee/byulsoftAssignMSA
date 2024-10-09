package com.example.authservice.service;

import com.example.authservice.dto.TokenResponseDto;
import com.example.authservice.entity.Member;
import com.example.authservice.mapper.MemberMapper;
import com.example.authservice.security.service.TokenCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberMapper memberMapper;
    private final TokenCreateService tokenCreateService;

    @Override
    public TokenResponseDto resetRefreshToken(String refreshToken) {
        Member member = memberMapper.findMemberByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("refresh token not found"));

        String renewAccessToken = tokenCreateService.createAccessToken(member);
        String renewRefreshToken = tokenCreateService.createRefreshToken();

        memberMapper.saveRefreshToken(member.getMemberId(), renewRefreshToken);

        return TokenResponseDto.builder()
                .accessToken(renewAccessToken)
                .refreshToken(renewRefreshToken)
                .build();
    }
}
