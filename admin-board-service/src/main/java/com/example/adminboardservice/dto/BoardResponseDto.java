package com.example.adminboardservice.dto;

import com.example.adminboardservice.entity.Board;
import com.example.adminboardservice.entity.BoardStar;
import com.example.adminboardservice.util.TimeChangerUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardResponseDto {
    private int boardId;
    private String memberNickname;
    private String boardTitle;
    private String boardRegion;
    private String boardContent;
    private int boardView;
    private String boardCreatedAt;
    private String boardUpdatedAt;
    private boolean boardIsDelete;
    private List<BoardStar> boardStars = new ArrayList<>();

    public static BoardResponseDto from(Board board, String memberNickname) {
        return BoardResponseDto.builder()
                .boardId(board.getBoardId())
                .memberNickname(memberNickname)
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .boardRegion(board.getBoardRegion())
                .boardView(board.getBoardView())
                .boardCreatedAt(TimeChangerUtil.timeChange(board.getBoardCreatedAt()))
                .boardUpdatedAt(TimeChangerUtil.timeChange(board.getBoardUpdatedAt()))
                .boardStars(board.getStars())
                .boardIsDelete(board.isBoardIsDelete())
                .build();
    }
}
