package com.example.userboardservice.dto;

import com.example.userboardservice.entity.Board;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {
    private int boardId;
    @NotNull(message = "작성자의 정보가 입력되지 않았습니다")
    private int memberId;

    @NotBlank(message = "제목을 입력해주세요")
    private String boardTitle;

    @NotBlank(message = "지역을 입력해주세요")
    private String boardRegion;

    @NotBlank(message = "총평을 입력해주세요")
    private String boardContent;

    private List<BoardStarRequestDto> boardStars = new ArrayList<>();

    public void validationCheck() {
        if (boardTitle.length() > 100) {
            throw new RuntimeException("제목의 최대 글자수를 초과했습니다");
        }

        if (boardRegion.length() > 4) {
            throw new RuntimeException("지역의 최대 글자수를 초과했습니다");
        }

        if (boardContent.length() > 1000) {
            throw new RuntimeException("총평의 최대 글자수를 초과했습니다");
        }
    }

    public Board toEntity(int memberId) {
        return  Board.builder()
                .boardId(boardId)
                .memberId(memberId)
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .boardRegion(boardRegion)
                .build();
    }
}
