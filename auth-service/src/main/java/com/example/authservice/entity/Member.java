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
}
