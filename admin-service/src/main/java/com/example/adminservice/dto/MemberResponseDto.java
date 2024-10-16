package com.example.adminservice.dto;

import com.example.adminservice.entity.Role;
import com.example.adminservice.util.TimeChangerUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
}
