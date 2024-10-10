package com.example.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    private int memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberRefreshToken;
    private Role memberRole;
    private boolean memberIsDelete;

    public void checkIfMemberIsDeleted () {
        if (memberIsDelete) {
            throw new RuntimeException("memberIsDelete");
        }
    }

    public void validateAdminRole () {
        if (memberRole == Role.ADMIN) {
            throw new RuntimeException("ADMIN 입니다");
        }
    }

    public void validateUserRole () {
        if (memberRole == Role.USER) {
            throw new RuntimeException("USER 입니다");
        }
    }
}
