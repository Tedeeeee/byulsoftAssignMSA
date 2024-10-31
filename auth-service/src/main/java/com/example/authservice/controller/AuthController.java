package com.example.authservice.controller;

import com.example.authservice.commonApi.BodyResponse;
import com.example.authservice.dto.AuthMemberDto;
import com.example.authservice.dto.TokenResponseDto;
import com.example.authservice.security.service.TokenCreateService;
import com.example.authservice.service.AuthService;
import com.example.authservice.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/health")
    public String healthCheck() {
        log.info("진입 성공");
        return "ingress를 통한 auth-service 진입 성공";
    }

    @PostMapping("/token/renew")
    public ResponseEntity<BodyResponse<String>> renewToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtil.getRefreshTokenInCookie(request, TokenCreateService.REFRESH_TOKEN_SUBJECT);

        TokenResponseDto tokenResponseDto = authService.resetRefreshToken(refreshToken);

        addRefreshTokenToResponseCookie(response, tokenResponseDto.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BodyResponse.success(tokenResponseDto.getAccessToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<BodyResponse<String>> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtil.getRefreshTokenInCookie(request, TokenCreateService.REFRESH_TOKEN_SUBJECT);
        authService.logout(refreshToken);

        deleteRefreshTokenToResponseCookie(response);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BodyResponse.success("로그아웃되었습니다"));
    }

    @PostMapping
    public void memberDataSave(@RequestBody AuthMemberDto authMemberDto) {
        authService.saveMember(authMemberDto);
    }

    private void addRefreshTokenToResponseCookie(HttpServletResponse response, String renewRefreshToken) {
        ResponseCookie refreshCookie = ResponseCookie.from(TokenCreateService.REFRESH_TOKEN_SUBJECT, renewRefreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(TokenCreateService.REFRESH_TOKEN_EXPIRES_IN)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }

    private void deleteRefreshTokenToResponseCookie(HttpServletResponse response) {
        ResponseCookie refreshCookie = ResponseCookie.from(TokenCreateService.REFRESH_TOKEN_SUBJECT, "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }
}
