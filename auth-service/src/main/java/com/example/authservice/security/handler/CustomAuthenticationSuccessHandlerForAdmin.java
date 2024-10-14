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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandlerForAdmin extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberMapper memberMapper;
    private final TokenCreateService tokenCreateService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = extractUsername(authentication);
        Member member = memberMapper.findMemberByMemberEmailForAdmin(email)
                .orElseThrow(() -> new RuntimeException("사용자의 정보가 존재하지 않습니다"));

        tokenCreateService.setSecretKeyForAdmin();

        String accessToken = tokenCreateService.createAccessToken(member);
        String refreshToken = tokenCreateService.createRefreshToken();

        // refreshToken 을 사용자의 DB에 저장
        memberMapper.saveRefreshToken(member.getMemberOriginalId(), refreshToken);

        // 응답에 토큰을 전달
        sendTokens(response, accessToken, refreshToken);
    }

    private void sendTokens(HttpServletResponse response, String accessToken, String refreshToken) throws IOException {

        ResponseCookie refreshCookie = ResponseCookie.from(TokenCreateService.REFRESH_TOKEN_SUBJECT, refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(TokenCreateService.REFRESH_TOKEN_EXPIRES_IN)
                .build();

        Map<String, String> responseValue = new HashMap<>();
        responseValue.put(TokenCreateService.ACCESS_TOKEN_SUBJECT, accessToken);

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
