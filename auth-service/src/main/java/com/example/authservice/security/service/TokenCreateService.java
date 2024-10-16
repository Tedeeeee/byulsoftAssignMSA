package com.example.authservice.security.service;

import com.example.authservice.entity.Member;
import com.example.authservice.entity.Role;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TokenCreateService {

    @Value("${jwt.secret.userKey}")
    private String userSecretKey;

    @Value("${jwt.secret.adminKey}")
    private String adminSecretKey;

    public static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    public static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    public static final int REFRESH_TOKEN_EXPIRES_IN = 604800;

    public String createAccessToken(Member member) {
        String secretKey = generateSecretKeyByRole(member.getMemberRole());

        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(member))
                .setSubject(ACCESS_TOKEN_SUBJECT)
                .setExpiration(createAccessTokenExpiredDate())
                .signWith(createSignature(secretKey), SignatureAlgorithm.HS256);
        return builder.compact();
    }

    public String createRefreshToken(Role role) {
        String secretKey = generateSecretKeyByRole(role);

        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader())
                .setSubject(REFRESH_TOKEN_SUBJECT)
                .setExpiration(createRefreshTokenExpiredDate())
                .signWith(createSignature(secretKey), SignatureAlgorithm.HS256);
        return builder.compact();
    }

    private String generateSecretKeyByRole(Role role) {
        if (Role.USER.equals(role)) {
            return userSecretKey;
        } else {
            return adminSecretKey;
        }
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return header;
    }

    private Map<String, String> createClaims(Member member) {
        Map<String, String> claims = new HashMap<>();

        claims.put("memberEmail", member.getMemberEmail());
        return claims;
    }

    private Date createAccessTokenExpiredDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, 1);
        return c.getTime();
    }

    private Date createRefreshTokenExpiredDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 7);
        return c.getTime();
    }

    public Key createSignature(String secretKey) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
