package com.example.authservice.dto;

import com.example.authservice.entity.Member;
import com.example.authservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthMemberDto {
    private int memberId;
    private String memberEmail;
    private String memberPassword;
    private Role memberRole;
    private boolean memberIsDelete;

    public static AuthMemberDto fromMap(Map<String, Object> map) {
        return new AuthMemberDto(
                (Integer) map.get("memberId"),
                (String) map.get("memberEmail"),
                (String) map.get("memberPassword"),
                Role.valueOf((String) map.get("memberRole")),
                (Boolean) map.get("memberIsDelete")
        );
    }

    public Member toEntity() {
        return Member.builder()
                .memberOriginalId(memberId)
                .memberEmail(memberEmail)
                .memberOriginalId(memberId)
                .memberPassword(memberPassword)
                .memberRole(memberRole)
                .memberIsDelete(memberIsDelete)
                .build();
    }
}
