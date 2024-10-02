package com.example.authservice.service;

import com.example.authservice.entity.Member;
import com.example.authservice.mapper.MemberMapper;
import com.example.authservice.security.service.TokenCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberMapper memberMapper;
    private final TokenCreateService tokenCreateService;

    @Override
    public Map<String, String> resetRefreshToken(String refreshToken) {
        Member member = memberMapper.findMemberByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("refresh token not found"));

        Map<String, String> tokenMap = new HashMap<>();
        String renewAccessToken = tokenCreateService.createAccessToken(member);
        String renewRefreshToken = tokenCreateService.createRefreshToken();

        tokenMap.put("accessToken", renewAccessToken);
        tokenMap.put("refreshToken", renewRefreshToken);

        memberMapper.saveRefreshToken(member.getMemberId(), renewRefreshToken);

        return tokenMap;
    }
}
