package com.example.adminboardservice.mapper;

import com.example.adminboardservice.dto.BoardResponseDto;
import com.example.adminboardservice.dto.SearchConditionDto;
import com.example.adminboardservice.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    List<Board> getAllBoard(SearchConditionDto searchConditionDto);
    int countTotalBoards(SearchConditionDto searchConditionDto);
    void softDeleteBoard(int boardId);
    Optional<Board> findBoardByBoardId(int boardId);
}
