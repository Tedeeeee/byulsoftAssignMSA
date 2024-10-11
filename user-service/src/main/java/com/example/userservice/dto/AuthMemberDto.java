package com.example.userservice.dto;

import com.example.userservice.entity.Member;
import com.example.userservice.entity.Role;
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

    public static AuthMemberDto from(Member member) {
        return AuthMemberDto.builder()
                .memberId(member.getMemberId())
                .memberEmail(member.getMemberEmail())
                .memberPassword(member.getMemberPassword())
                .memberRole(Role.USER)
                .memberIsDelete(member.isMemberIsDelete())
                .build();
    }
}
