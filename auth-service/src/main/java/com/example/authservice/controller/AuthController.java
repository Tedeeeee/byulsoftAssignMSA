package com.example.authservice.controller;

import com.example.authservice.entity.Member;
import com.example.authservice.entity.Role;
import com.example.authservice.mapper.MemberMapper;
import com.example.authservice.security.service.TokenCreateService;
import com.example.authservice.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping()
    public ResponseEntity<String> hello() {
        System.out.println("hello");
        return new ResponseEntity<>("성공", HttpStatus.OK);
    }

    @PostMapping("/token/renew")
    public ResponseEntity<String> renewToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = findRefreshTokenInCookie(request);

        Map<String, String> tokenMap = authService.resetRefreshToken(refreshToken);

        addRefreshTokenToResponseCookie(response, tokenMap);

        return new ResponseEntity<>(tokenMap.get("accessToken"), HttpStatus.OK);
    }

    private static void addRefreshTokenToResponseCookie(HttpServletResponse response, Map<String, String> tokenMap) {
        String renewRefreshToken = tokenMap.get("refreshToken");

        ResponseCookie refreshCookie = ResponseCookie.from("RefreshToken", renewRefreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(604800)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }

    private static String findRefreshTokenInCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("RefreshToken"))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("존재하지 않는 토큰입니다"));
    }
}
