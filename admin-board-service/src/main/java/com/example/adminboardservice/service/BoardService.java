package com.example.adminboardservice.service;

import com.example.adminboardservice.dto.BoardListResponseDto;
import com.example.adminboardservice.dto.BoardResponseDto;
import com.example.adminboardservice.dto.SearchConditionDto;

public interface BoardService {
    /**
     * 설명 : 게시글 전체 가져오기
     *       사용자도 검색 조건에 포함하여 검색하기 가능
     * @since : 2024.10.08
     * @author : T.S YUN
     */
    BoardListResponseDto getAllBoards(SearchConditionDto searchConditionDto);

    /**
     * 설명 : 게시글 강제 삭제
     * @since : 2024.10.08
     * @author : T.S YUN
     */
    void deleteBoardByBoardId(int boardId);

    /**
     * 설명 : 게시글 확인
     * @since : 2024.10.18
     * @author : T.S YUN
     */
    BoardResponseDto findBoardById(int boardId);
}
