package com.example.authservice.security.handler;

import com.example.authservice.entity.Member;
import com.example.authservice.mapper.MemberMapper;
import com.example.authservice.security.service.TokenCreateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberMapper memberMapper;
    private final TokenCreateService tokenCreateService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String memberEmail = extractUsername(authentication);
        Member member = memberMapper.findMemberByMemberEmail(memberEmail)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾지 못했습니다"));

        String accessToken = tokenCreateService.createAccessToken(member);
        String refreshToken = tokenCreateService.createRefreshToken();

        // refreshToken 을 사용자의 DB에 저장
        memberMapper.saveRefreshToken(member.getMemberId(), refreshToken);

        // 응답에 토큰을 전달
        sendTokens(response, accessToken, refreshToken, member);
    }

    private void sendTokens(HttpServletResponse response, String accessToken, String refreshToken, Member member) throws IOException {
        ResponseCookie refreshCookie = ResponseCookie.from("RefreshToken", refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(604800)
                .build();

        Map<String, String> responseValue = new HashMap<>();
        responseValue.put("accessToken", accessToken);
        responseValue.put("nickname", member.getMemberNickname());

        String jsonResponseBody = objectMapper.writeValueAsString(responseValue);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        response.getWriter().write(jsonResponseBody);
        response.getWriter().flush();
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
