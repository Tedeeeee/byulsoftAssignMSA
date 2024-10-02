package com.example.userservice.dto;

import com.example.userservice.entity.Member;
import com.example.userservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
    private int memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberNickname;
    private String memberName;
    private String memberPhoneNumber;
    private String memberRefreshToken;
    private boolean memberIsDelete;
    private Role memberRole;
    private LocalDateTime memberCreatedAt;
    private LocalDateTime memberUpdatedAt;

    public static MemberResponseDto from(Member member) {
        return MemberResponseDto.builder()
                .memberId(member.getMemberId())
                .memberEmail(member.getMemberEmail())
                .memberPassword(member.getMemberPassword())
                .memberNickname(member.getMemberNickname())
                .memberName(member.getMemberName())
                .memberPhoneNumber(member.getMemberPhoneNumber())
                .memberRole(member.getMemberRole())
                .memberCreatedAt(member.getMemberCreatedAt())
                .memberUpdatedAt(member.getMemberUpdatedAt())
                .build();
    }
}
