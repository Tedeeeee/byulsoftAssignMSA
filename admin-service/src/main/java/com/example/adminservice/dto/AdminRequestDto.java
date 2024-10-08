package com.example.adminservice.dto;

import com.example.adminservice.entity.AdminMember;
import com.example.adminservice.util.ValidationUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminRequestDto {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @NotBlank
    private String adminEmail;

    @NotBlank
    private String adminPassword;

    @NotBlank
    private String adminNickname;
    private String adminName;
    private String adminPhoneNumber;

    public void memberSignupValidator() {
        ValidationUtil.emailValidationCheck(adminEmail);
        ValidationUtil.nicknameValidationCheck(adminNickname);
    }

    public AdminMember toEntity() {
        return AdminMember.builder()
                .adminEmail(adminEmail)
                .adminPassword(passwordEncoder.encode(adminPassword))
                .adminNickname(adminNickname)
                .adminName(adminName)
                .adminPhoneNumber(adminPhoneNumber)
                .build();
    }
}
