package com.example.userservice.dto;

import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    private static final String EMAIL_VALIDATION = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PASSWORD_VALIDATION = "^(?=.*[A-Z])(?=.*[!@#$%^*+=-]).{6,}$";

    @NotBlank
    private int memberId;

    @NotBlank
    private String memberEmail;

    @NotBlank
    private String memberPassword;

    public void validation() {
        if (!memberEmail.matches(EMAIL_VALIDATION)) {
            throw new ValidationException("이메일이 유효하지 않습니다");
        }

        if (!memberPassword.matches(PASSWORD_VALIDATION)) {
            throw new ValidationException("유효한 비밀번호를 입력해주세요");
        }
    }
}
