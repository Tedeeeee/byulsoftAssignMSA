package com.example.adminservice.dto;

import com.example.adminservice.entity.AdminMember;
import com.example.adminservice.entity.Role;
import com.example.adminservice.util.TimeChangerUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminResponseDto {
    private int adminId;
    private String adminEmail;
    private String adminPassword;
    private String adminNickname;
    private String adminName;
    private String adminPhoneNumber;
    private Role adminRole;
    private String adminCreatedAt;
    private String adminUpdatedAt;

    public static AdminResponseDto from(AdminMember adminMember) {
        return AdminResponseDto.builder()
                .adminId(adminMember.getAdminId())
                .adminEmail(adminMember.getAdminEmail())
                .adminPassword(adminMember.getAdminPassword())
                .adminNickname(adminMember.getAdminNickname())
                .adminName(adminMember.getAdminName())
                .adminPhoneNumber(adminMember.getAdminPhoneNumber())
                .adminRole(adminMember.getAdminRole())
                .adminCreatedAt(TimeChangerUtil.timeChange(adminMember.getAdminCreatedAt()))
                .adminUpdatedAt(TimeChangerUtil.timeChange(adminMember.getAdminUpdatedAt()))
                .build();
    }
}