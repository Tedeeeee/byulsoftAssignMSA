package com.example.adminservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminListResponseDto {
    private List<AdminResponseDto> adminResponseDtoList;
    private int totalMembers;

    public static AdminListResponseDto from (List<AdminResponseDto> adminResponseDtoList, int totalPage) {
        return AdminListResponseDto.builder()
                .adminResponseDtoList(adminResponseDtoList)
                .totalMembers(totalPage)
                .build();
    }
}
