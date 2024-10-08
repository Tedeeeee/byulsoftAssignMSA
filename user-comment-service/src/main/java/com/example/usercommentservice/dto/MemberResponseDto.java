package com.example.usercommentservice.dto;

import com.example.usercommentservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
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

    public void checkMemberIdNull() {
        if (memberId == 0) {
            throw new RuntimeException("사용자가 존재하지 않습니다");
        }
    }

    public void checkSameMemberId(int memberId) {
        if (memberId != this.memberId) {
            throw new RuntimeException("사용자가 일치하지 않습니다");
        }
    }
}