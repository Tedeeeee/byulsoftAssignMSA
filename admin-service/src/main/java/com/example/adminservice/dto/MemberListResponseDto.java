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
public class MemberListResponseDto {
    private List<MemberResponseDto> memberResponseDtoList;
    private int totalMembers;

    public static MemberListResponseDto from (List<MemberResponseDto> memberResponseDtoList, int totalPage) {
        return MemberListResponseDto.builder()
                .memberResponseDtoList(memberResponseDtoList)
                .totalMembers(totalPage)
                .build();
    }
}