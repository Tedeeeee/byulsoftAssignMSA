package com.example.authservice.security.filter;

import com.example.authservice.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        super.setAuthenticationManager(authenticationManager);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest = null;
        try {
            authRequest = getAuthRequest(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) throws IOException {
        Member member = objectMapper.readValue(request.getReader(), Member.class);

        // 여기서 패턴을 컴파일러하는 로직이 필요

        return new UsernamePasswordAuthenticationToken(member.getMemberEmail(), member.getMemberPassword());
    }
}
