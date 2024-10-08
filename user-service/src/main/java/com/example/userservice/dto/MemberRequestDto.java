package com.example.userservice.dto;

import com.example.userservice.entity.Member;
import com.example.userservice.util.ValidationUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @NotBlank
    private int memberId;

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
                .memberPassword(passwordEncoder.encode(memberPassword))
                .memberNickname(memberNickname)
                .memberName(memberName)
                .memberPhoneNumber(memberPhoneNumber)
                .build();
    }
}
