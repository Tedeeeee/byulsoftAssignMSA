package com.example.userboardservice.mapper;

import com.example.userboardservice.dto.SearchConditionDto;
import com.example.userboardservice.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void save(Board board);
    int countTotalBoards();
    List<Integer> getBoardIdsByBasic(SearchConditionDto searchConditionDto);
    List<Board> getPostsByPostIdList(List<Integer> postIdList);
    Board findBoardByBoardId(int boardId);
    void updateBoard(Board board);
    void deleteBoard(int boardId);
    boolean checkBoardExist(int boardId);
}
