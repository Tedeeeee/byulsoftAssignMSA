package com.example.apigatewayservice;

import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;

public class Token {
    private static final String TOKEN_TARGET = "Bearer ";

    private final String value;

    public Token(String value) {
        this.value = value;
    }

    public static Token replaceHeaderTargetToken(String headerTargetToken) {
        return new Token(headerTargetToken.replace(TOKEN_TARGET, ""));
    }

    public Claims getClaims(SecretKey secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(value)
                .getBody();
    }

    public void validationToken(SecretKey secretKey) {
        try {
            getClaims(secretKey);

        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효기간이 지난 JWT 를 사용하였습니다.");
        } catch (MalformedJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "잘못된 형식의 JWT 입니다.");
        } catch (UnsupportedJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "지원되지 않는 JWT 입니다.");
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT 입니다.");
        } catch (Exception e) {
            throw new RuntimeException("토큰 검증 중 예기치 못한 오류가 발생했습니다.");
        }
    }
    public boolean isAdminCheck(SecretKey secretKey) {
        String role = getClaims(secretKey)
                .get("role", String.class);

        return "ADMIN".equals(role);
    }

    public String getMemberEmail(SecretKey secretKey) {
        Claims body = getClaims(secretKey);

        return body.get("memberEmail", String.class);
    }
}
