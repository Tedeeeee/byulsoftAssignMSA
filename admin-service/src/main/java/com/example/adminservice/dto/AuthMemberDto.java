package com.example.adminservice.dto;

import com.example.adminservice.entity.AdminMember;
import com.example.adminservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthMemberDto {
    private int memberId;
    private String memberEmail;
    private String memberPassword;
    private Role memberRole;
    private boolean memberIsDelete;

    public static AuthMemberDto from(AdminMember member) {
        return AuthMemberDto.builder()
                .memberId(member.getAdminId())
                .memberEmail(member.getAdminEmail())
                .memberPassword(member.getAdminPassword())
                .memberRole(Role.ADMIN)
                .memberIsDelete(member.isAdminIsDelete())
                .build();
    }
}
