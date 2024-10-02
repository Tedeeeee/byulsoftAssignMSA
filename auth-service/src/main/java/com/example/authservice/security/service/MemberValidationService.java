package com.example.authservice.security.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MemberValidationService {
    private static final String EMAIL_CONFIRM = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PASSWORD_CONFIRM = "^(?=.*[A-Z])(?=.*[!@#$%^*+=-]).{6,}$";

    public static void memberInputDataValidation(String email, String password) {
        if (!email.matches(EMAIL_CONFIRM)) {
            throw new UsernameNotFoundException("이메일 형식을 올바르게 입력해주세요");
        }

        if (!password.matches(PASSWORD_CONFIRM)) {
            throw new BadCredentialsException("비밀번호 형식을 올바르게 입력해주세요");
        }
    }
}
