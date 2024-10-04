package com.example.adminservice.dto;

import com.example.adminservice.entity.Member;
import com.example.adminservice.util.ValidationUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminRequestDto {
    @NotBlank
    private String memberEmail;

    @NotBlank
    private String memberPassword;

    @NotBlank
    private String memberNickname;
    private String memberName;
    private String memberPhoneNumber;

    public void memberSignupValidator() {
        ValidationUtil.emailValidationCheck(memberEmail);
        ValidationUtil.nicknameValidationCheck(memberNickname);
    }

    public Member toEntity() {
        return Member.builder()
                .memberEmail(memberEmail)
                .memberPassword(memberPassword)
                .memberNickname(memberNickname)
                .memberName(memberName)
                .memberPhoneNumber(memberPhoneNumber)
                .build();
    }
}
