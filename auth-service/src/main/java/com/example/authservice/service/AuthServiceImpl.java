package com.example.authservice.service;

import com.example.authservice.dto.TokenResponseDto;
import com.example.authservice.entity.Member;
import com.example.authservice.mapper.MemberMapper;
import com.example.authservice.security.service.TokenCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberMapper memberMapper;
    private final TokenCreateService tokenCreateService;

    @Override
    public TokenResponseDto resetRefreshToken(String refreshToken) {
        Member member = memberMapper.findMemberByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("refreshtoken이 올바르지 않습니다"));

        String renewAccessToken = tokenCreateService.createAccessToken(member);
        String renewRefreshToken = tokenCreateService.createRefreshToken();

        memberMapper.saveRefreshToken(member.getMemberOriginalId(), renewRefreshToken);

        return TokenResponseDto.builder()
                .accessToken(renewAccessToken)
                .refreshToken(renewRefreshToken)
                .build();
    }
}
