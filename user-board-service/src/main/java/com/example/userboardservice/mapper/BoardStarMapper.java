package com.example.userboardservice.mapper;

import com.example.userboardservice.dto.SearchConditionDto;
import com.example.userboardservice.entity.BoardStar;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardStarMapper {
    void boardStarSaveAll(List<BoardStar> boardStars);
    void deleteBoardStarByBoardId(int boardId);
    List<Integer> getBoardIdsBySortType(SearchConditionDto searchConditionDto);
}
