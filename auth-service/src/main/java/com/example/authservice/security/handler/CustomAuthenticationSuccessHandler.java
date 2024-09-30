package com.example.authservice.security.handler;

import com.example.authservice.entity.Member;
import com.example.authservice.mapper.MemberMapper;
import com.example.authservice.security.service.TokenCreateService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberMapper memberMapper;
    private final TokenCreateService tokenCreateService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String memberEmail = extractUsername(authentication);
        Member member = memberMapper.findMemberByMemberEmail(memberEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Member not found"));

        String accessToken = tokenCreateService.createAccessToken(member);
        String refreshToken = tokenCreateService.createRefreshToken();

        // refreshToken 을 사용자의 DB에 저장
        memberMapper.saveRefreshToken(member.getMemberId(), refreshToken);

        // 응답에 토큰을 전달 ( 쿠키 : HttpOnly )
        sendTokens(response, accessToken, refreshToken, member);
    }

    private void sendTokens(HttpServletResponse response, String accessToken, String refreshToken, Member member) throws IOException {
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .path("/")
                .maxAge(3600)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(604800)
                .build();

        // 한글의 닉네임인 경우 비 ASCII 문자이기 때문에 오류 발생으로 한글을 읽을 수 있도록 변경
        String encodedNickname = URLEncoder.encode(member.getMemberNickname(), StandardCharsets.UTF_8);
        ResponseCookie nicknameCookie = ResponseCookie.from("nickname", encodedNickname)
                .path("/")
                .maxAge(3600)
                .build();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, nicknameCookie.toString());
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
