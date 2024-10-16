package com.example.userservice.entity;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private int memberId;
    private String memberEmail;
    @Setter
    private String memberPassword;
    private String memberNickname;
    private String memberName;
    private String memberPhoneNumber;
    private boolean memberIsDelete;
    private Role memberRole;
    private LocalDateTime memberCreatedAt;
    private LocalDateTime memberUpdatedAt;

    public void checkPassword(String originPassword) {
        if (!passwordEncoder.matches(originPassword, memberPassword)){
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }
    }

    public String changePasswordEncoding(String memberPassword) {
        return passwordEncoder.encode(memberPassword);
    }

    public String getMemberNickname() {
        if (StringUtils.hasText(memberNickname)) {
            return memberNickname;
        }
        return "탈퇴한 회원입니다";
    }
}
