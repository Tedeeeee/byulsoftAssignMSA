package com.example.authservice.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // Map 형태로 전달 vs 객체 형태로 제작 후 전달
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.UNAUTHORIZED);
        errorResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());

        if (exception instanceof BadCredentialsException) {
            errorResponse.put("message", exception.getMessage());
        } else if (exception instanceof UsernameNotFoundException) {
            errorResponse.put("message", exception.getMessage());
        } else {
            errorResponse.put("message", exception.getMessage());
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String responseBody = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().write(responseBody);
        response.getWriter().flush();
    }
}
