package com.example.userboardservice.dto;

import com.example.userboardservice.entity.Board;
import com.example.userboardservice.entity.BoardStar;
import com.example.userboardservice.util.TimeChangerUtil;
import lombok.*;

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
                .build();
    }

    public static BoardResponseDto myBoardFrom(Board board) {
        return BoardResponseDto.builder()
                .boardId(board.getBoardId())
                .boardTitle(board.getBoardTitle())
                .boardCreatedAt(TimeChangerUtil.timeChange(board.getBoardCreatedAt()))
                .build();
    }
}
