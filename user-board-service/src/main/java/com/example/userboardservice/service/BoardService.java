package com.example.userboardservice.service;

import com.example.userboardservice.dto.BoardListResponseDto;
import com.example.userboardservice.dto.BoardRequestDto;
import com.example.userboardservice.dto.BoardResponseDto;
import com.example.userboardservice.dto.SearchConditionDto;

import java.util.List;

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
    BoardResponseDto findBoardById(int boardId);

    /**
     * 설명 : 게시글 수정
     * @since : 2024.10.07
     * @author : T.S YUN
     */
    void updateBoard(BoardRequestDto boardRequestDto, String memberEmail);

    /**
     * 설명 : 게시글 정렬
     * @since : 2024.10.07
     * @author : T.S YUN
     */
    BoardListResponseDto getPostsByCondition(SearchConditionDto searchConditionDto);

    /**
     * 설명 : 게시글 삭제 ( soft 삭제 )
     * @since : 2024.10.07
     * @author : T.S YUN
     */
    void deleteBoardById(int boardId, String memberEmail);

    /**
     * 설명 : BoardId 를 통한 Board 존재 유무 파악
     *       Feign 을 위한 서비스
     * @since : 2024.10.07
     * @author : T.S YUN
     */
    boolean existBoardByBoardId(int boardId);

    /**
     * 설명 : 내가 작성한 board 찾기
     * @since : 2024.10.15
     * @author : T.S YUN
     */
    List<BoardResponseDto> getMyBoards(String memberEmail);
}
