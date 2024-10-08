package com.example.authservice.security.handler;

import com.example.authservice.entity.AdminMember;
import com.example.authservice.entity.Member;
import com.example.authservice.mapper.AdminMapper;
import com.example.authservice.mapper.MemberMapper;
import com.example.authservice.security.service.TokenCreateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberMapper memberMapper;
    private final AdminMapper adminMapper;
    private final TokenCreateService tokenCreateService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = extractUsername(authentication);
        Member member = memberMapper.findMemberByMemberEmail(email);
        AdminMember adminMember = adminMapper.findAdminByAdminEmail(email);

        if (member == null && adminMember == null) {
            throw new UsernameNotFoundException("존재하지 않는 사용자입니다");
        }

        String accessToken = tokenCreateService.createAccessToken(member);
        String refreshToken = tokenCreateService.createRefreshToken();

        // refreshToken 을 사용자의 DB에 저장
        memberMapper.saveRefreshToken(member.getMemberId(), refreshToken);

        // 응답에 토큰을 전달
        sendTokens(response, accessToken, refreshToken, member);
    }

    private void sendTokens(HttpServletResponse response, String accessToken, String refreshToken, Member member) throws IOException {
        System.out.println(TokenCreateService.REFRESH_TOKEN_SUBJECT);
        ResponseCookie refreshCookie = ResponseCookie.from(TokenCreateService.REFRESH_TOKEN_SUBJECT, refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(TokenCreateService.REFRESH_TOKEN_EXPIRES_IN)
                .build();

        Map<String, String> responseValue = new HashMap<>();
        responseValue.put(TokenCreateService.ACCESS_TOKEN_SUBJECT, accessToken);
        // accessToken 이 메모리에 저장되면서 nickname을 꼭 사용해야 하는가에 대한 고려
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
