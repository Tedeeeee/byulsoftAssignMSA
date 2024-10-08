package com.example.adminboardservice.service;

import com.example.adminboardservice.dto.BoardListResponseDto;
import com.example.adminboardservice.dto.BoardResponseDto;
import com.example.adminboardservice.dto.SearchConditionDto;
import com.example.adminboardservice.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    @Override
    public BoardListResponseDto getAllBoards(SearchConditionDto searchConditionDto) {
        int totalPageCount = getTotalPageCount();

        searchConditionDto.checkValidationPageNumber(totalPageCount);

        List<BoardResponseDto> allBoard = boardMapper.getAllBoard(searchConditionDto).stream()
                .map(board -> {
                    return BoardResponseDto.from(board, null);
                })
                .toList();

        return BoardListResponseDto.from(allBoard, totalPageCount);
    }

    @Override
    @Transactional
    public void deleteBoardByBoardId(int boardId) {
        boardMapper.softDeleteBoard(boardId);
    }

    private int getTotalPageCount() {
        int totalBoards = boardMapper.countTotalBoards();
        return (int) Math.ceil((double) totalBoards / 5);
    }
}
