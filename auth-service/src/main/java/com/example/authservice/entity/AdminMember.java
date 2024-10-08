package com.example.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminMember {
    private int adminId;
    private String adminEmail;
    private String adminPassword;
    private String adminNickname;
    private String adminName;
    private String adminPhoneNumber;
    private String adminRefreshToken;
    private boolean adminIsDelete;
    private Role adminRole;
    private LocalDateTime adminCreatedAt;
    private LocalDateTime adminUpdatedAt;
}

