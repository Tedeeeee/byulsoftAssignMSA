package com.example.userboardservice.service;

import com.example.userboardservice.dto.BoardRequestDto;
import com.example.userboardservice.dto.BoardResponseDto;
import com.example.userboardservice.dto.BoardStarRequestDto;
import com.example.userboardservice.entity.Board;
import com.example.userboardservice.entity.BoardStar;
import com.example.userboardservice.mapper.BoardMapper;
import com.example.userboardservice.mapper.BoardStarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;
    private final BoardStarMapper boardStarMapper;

    @Override
    @Transactional
    public void addBoard(BoardRequestDto boardRequestDto, String memberEmail) {
        // 사용자의 정보 가져오기 ( openFeign )

        // 가져온 정보 validation 진행
        boardRequestDto.validationCheck(memberEmail);

        Board board = boardRequestDto.toEntity();
        boardMapper.save(board);

        bulkInsertBoardStars(boardRequestDto.getBoardStars(), board.getBoardId());
    }

    @Override
    public BoardResponseDto findBoardById(int boardId, String memberEmail) {
        Board board = boardMapper.findBoardByBoardId(boardId);

        // email 을 통해 사용자 정보 가져오기

        // 가져온 사용자의 ID 와 게시글의 ID 비교

        // 사용자 정보 가져와서 memberNickname 셋팅
        String memberNickname = "";
        return BoardResponseDto.from(board, memberNickname);
    }


    private void bulkInsertBoardStars(List<BoardStarRequestDto> boardStarRequestDtoList, int boardId) {
        boardStarRequestDtoList.forEach(BoardStarRequestDto::validationCheck);

        List<BoardStar> boardStarList = new ArrayList<>();
        int size = boardStarRequestDtoList.size();
        for (int i = 0; i < size; i++) {
            BoardStar boardStar = boardStarRequestDtoList.get(i).toEntity(boardId, i + 1);
            boardStarList.add(boardStar);
        }

        boardStarMapper.boardStarSaveAll(boardStarList);
    }
}
