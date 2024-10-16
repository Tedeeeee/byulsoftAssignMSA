package com.example.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    private int memberId;
    private int memberOriginalId;
    private String memberEmail;
    private String memberPassword;
    private String memberRefreshToken;
    private Role memberRole;
    private boolean memberIsDelete;

    public void checkIfMemberIsDeleted () {
        if (memberIsDelete) {
            throw new RuntimeException("탈퇴한 사용자입니다");
        }
    }

    public String mySecretKey(String adminSecretKey, String userSecretKey) {
        if (Role.USER.equals(memberRole)) {
            return userSecretKey;
        } else {
            return adminSecretKey;
        }
    }
}
