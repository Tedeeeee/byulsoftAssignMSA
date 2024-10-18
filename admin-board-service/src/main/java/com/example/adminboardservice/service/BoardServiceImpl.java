package com.example.adminboardservice.service;

import com.example.adminboardservice.client.MemberServiceClient;
import com.example.adminboardservice.dto.BoardListResponseDto;
import com.example.adminboardservice.dto.BoardResponseDto;
import com.example.adminboardservice.dto.SearchConditionDto;
import com.example.adminboardservice.entity.Board;
import com.example.adminboardservice.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final MemberServiceClient memberServiceClient;

    @Override
    public BoardListResponseDto getAllBoards(SearchConditionDto searchConditionDto) {
        int totalPageCount = getTotalPageCount(searchConditionDto);
        searchConditionDto.checkValidationPageNumber(totalPageCount);

        List<BoardResponseDto> allBoard = getBoardResponseDtoList(searchConditionDto);

        return BoardListResponseDto.from(allBoard, totalPageCount);
    }

    private List<BoardResponseDto> getBoardResponseDtoList(SearchConditionDto searchConditionDto) {
        String nickname = memberServiceClient.getMemberNicknameByMemberId(searchConditionDto.getMemberId());
        return boardMapper.getAllBoard(searchConditionDto).stream()
                .map(board -> BoardResponseDto.from(board, nickname))
                .toList();
    }

    @Override
    @Transactional
    public void deleteBoardByBoardId(int boardId) {
        boardMapper.softDeleteBoard(boardId);
    }

    @Override
    public BoardResponseDto findBoardById(int boardId) {
        Board board = boardMapper.findBoardByBoardId(boardId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글 입니다"));
        BoardResponseDto boardResponseDto = BoardResponseDto.from(board);

        // board의 id를 통해 사용자 정보 가져오기
        String nickname = memberServiceClient.getMemberNicknameByMemberId(board.getMemberId());
        boardResponseDto.setMemberNickname(nickname);

        return boardResponseDto;
    }

    private int getTotalPageCount(SearchConditionDto searchConditionDto) {
        int totalBoards = boardMapper.countTotalBoards(searchConditionDto);
        return (int) Math.ceil((double) totalBoards / 5);
    }
}
