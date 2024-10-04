package com.example.userboardservice.service;

import com.example.userboardservice.dto.BoardRequestDto;
import com.example.userboardservice.dto.BoardResponseDto;

public interface BoardService {
    /**
     * 설명 : 게시글 저장
     * @since : 2024.10.04
     * @author : T.S YUN
     */
    void addBoard(BoardRequestDto boardRequestDto, String memberEmail);

    /**
     * 설명 : 게시글 확인
     * @since : 2024.10.04
     * @author : T.S YUN
     */
    BoardResponseDto findBoardById(int boardId, String memberEmail);
}
