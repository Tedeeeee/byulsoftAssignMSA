package com.example.admincommentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponseDto {
    private int adminId;

    public void checkSameAdminId(int adminId) {
        if (this.adminId != adminId) {
            throw new RuntimeException("사용자가 일치하지 않습니다");
        }
    }

}
