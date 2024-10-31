package com.example.userreportservice.dto;

import com.example.userreportservice.entity.Role;
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
    private boolean memberIsDelete;
    private Role memberRole;
    private String memberCreatedAt;
    private String memberUpdatedAt;
}
