package com.example.userboardservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardListResponseDto {
    private List<BoardResponseDto> boards;
    private int totalPages;

    public static BoardListResponseDto from(List<BoardResponseDto> boards, int totalPages) {
        return BoardListResponseDto.builder()
                .boards(boards)
                .totalPages(totalPages)
                .build();
    }
}
