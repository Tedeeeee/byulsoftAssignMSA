package com.example.authservice.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CookieUtil {
    public static String getRefreshTokenInCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new RuntimeException("요청에 쿠키가 담겨있지 않습니다");
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("존재하지 않는 토큰입니다"));
    }
}
