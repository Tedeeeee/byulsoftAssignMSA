package com.example.userservice.dto;

import com.example.userservice.entity.Member;
import com.example.userservice.entity.Role;
import com.example.userservice.util.TimeChangerUtil;
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
    private boolean memberIsDelete;
    private Role memberRole;
    private String memberCreatedAt;
    private String memberUpdatedAt;

    public static MemberResponseDto from(Member member) {
        return MemberResponseDto.builder()
                .memberId(member.getMemberId())
                .memberEmail(member.getMemberEmail())
                .memberNickname(member.getMemberNickname())
                .memberName(member.getMemberName())
                .memberPhoneNumber(member.getMemberPhoneNumber())
                .memberIsDelete(member.isMemberIsDelete())
                .memberRole(member.getMemberRole())
                .memberCreatedAt(TimeChangerUtil.timeChange(member.getMemberCreatedAt()))
                .memberUpdatedAt(TimeChangerUtil.timeChange(member.getMemberUpdatedAt()))
                .build();
    }
}
