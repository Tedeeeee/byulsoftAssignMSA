package com.example.admincommentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentListResponseDto {
    private List<CommentResponseDto> commentResponseDtoList;
    private int totalPage;

    public static CommentListResponseDto from(List<CommentResponseDto> commentResponseDtoList, int totalPage) {
        return CommentListResponseDto.builder()
                .commentResponseDtoList(commentResponseDtoList)
                .totalPage(totalPage)
                .build();
    }
}
