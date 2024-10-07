package com.example.adminservice.util;

public class ValidationUtil {

    private ValidationUtil() {}
    
    private static final String EMAIL_CONFIRM = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PASSWORD_CONFIRM = "^(?=.*[A-Z])(?=.*[!@#$%^*+=-]).{6,}$";
    private static final String NICKNAME_CONFIRM = "^[a-zA-Z가-힝0-9]+$";

    public static void emailValidationCheck(String email) {
        if (!email.matches(EMAIL_CONFIRM)) {
            throw new RuntimeException("이메일 형식을 올바르게 입력해주세요");
        }
    }

    public static void nicknameValidationCheck(String nickname) {
        if (!nickname.matches(NICKNAME_CONFIRM)) {
            throw new RuntimeException("닉네임 형식을 올바르게 입력해주세요");
        }
    }
}
