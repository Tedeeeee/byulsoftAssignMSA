package com.example.authservice.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // Map 형태로 전달 vs 객체 형태로 제작 후 전달
        Map<String, Object> errorResponse = new HashMap<>();

        if (exception instanceof BadCredentialsException) {
            errorResponse.put("status", HttpStatus.UNAUTHORIZED);
            errorResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
            errorResponse.put("message", exception.getMessage());
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String responseBody = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().write(responseBody);
        response.getWriter().flush();
    }
}
