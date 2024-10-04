package com.example.userboardservice.dto;

import com.example.userboardservice.entity.BoardStar;
import com.example.userboardservice.entity.BoardStarType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardStarRequestDto {
    @NotBlank
    private BoardStarType boardStarType;
    private String boardStarShortReview;
    @NotBlank
    private int boardStarRating;

    public void validationCheck() {
        if (boardStarShortReview.length() > 100) {
            throw new RuntimeException(boardStarType + "의 한줄평이 최대 글자수를 초과했습니다");
        }

        if (boardStarRating < 0 || boardStarRating > 5) {
            throw new RuntimeException(boardStarType + "의 별점이 한개 이상 혹은 5개를 초과하지 않아야 합니다");
        }
    }

    public BoardStar toEntity(int boardId, int sortNo) {
        return BoardStar.builder()
                .boardId(boardId)
                .boardStarType(boardStarType)
                .boardStarShortReview(boardStarShortReview)
                .boardStarRating(boardStarRating)
                .sortNo(sortNo)
                .build();
    }
}
