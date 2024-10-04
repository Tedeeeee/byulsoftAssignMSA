package com.example.usercommentservice.util;


public class ValidationUtil {
    private static final String CONTENT_CONFIRM = "^[a-zA-Z0-9가-힝\\\\s.,?!]+$";

    public static void checkContentValidate(String content) {
        if (!content.matches(CONTENT_CONFIRM)) {
            throw new RuntimeException("특정 특수 문자 혹은 HTML 태그는 사용할 수 없습니다");
        }
    }
}
