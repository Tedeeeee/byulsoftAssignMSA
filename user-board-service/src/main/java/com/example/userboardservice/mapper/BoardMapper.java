package com.example.userboardservice.mapper;

import com.example.userboardservice.entity.Board;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    void save(Board board);
    Board findBoardByBoardId(int boardId);
}
