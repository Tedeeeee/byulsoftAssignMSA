package com.example.userboardservice.service;

import com.example.userboardservice.client.CommentServiceClient;
import com.example.userboardservice.client.MemberServiceClient;
import com.example.userboardservice.dto.*;
import com.example.userboardservice.entity.Board;
import com.example.userboardservice.entity.BoardStar;
import com.example.userboardservice.mapper.BoardMapper;
import com.example.userboardservice.mapper.BoardStarMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;
    private final BoardStarMapper boardStarMapper;
    private final MemberServiceClient memberServiceClient;
    private final CommentServiceClient commentServiceClient;

    @Override
    @Transactional
    public void addBoard(BoardRequestDto boardRequestDto, String memberEmail) {
        MemberResponseDto member = memberServiceClient.getMemberByMemberEmail(memberEmail);

        member.checkMemberIdNull();

        boardRequestDto.validationCheck();

        Board board = boardRequestDto.toEntity(member.getMemberId());
        boardMapper.save(board);

        bulkInsertBoardStars(boardRequestDto.getBoardStars(), board.getBoardId());
    }

    @Override
    public BoardResponseDto findBoardById(int boardId) {
        Board board = boardMapper.findBoardByBoardId(boardId);
        BoardResponseDto boardResponseDto = BoardResponseDto.from(board);

        String nickname = memberServiceClient.getMemberNicknameByMemberId(board.getMemberId());
        boardResponseDto.setMemberNickname(nickname);

        return boardResponseDto;
    }

    @Override
    @Transactional
    public void updateBoard(BoardRequestDto boardRequestDto, String memberEmail) {
        MemberResponseDto member = memberServiceClient.getMemberByMemberEmail(memberEmail);

        member.checkSameMemberId(boardRequestDto.getMemberId());
        boardRequestDto.validationCheck();

        Board board = boardRequestDto.toEntity(member.getMemberId());

        boardMapper.updateBoard(board);

        boardStarMapper.deleteBoardStarByBoardId(board.getBoardId());

        bulkInsertBoardStars(boardRequestDto.getBoardStars(), board.getBoardId());
    }

    @Override
    public BoardListResponseDto getPostsByCondition(SearchConditionDto searchConditionDto) {
        List<Integer> postList = getFilteredPostIdsByCondition(searchConditionDto);

        if (postList.isEmpty()) {
            throw new RuntimeException("검색 결과가 존재하지 않습니다");
        }

        List<Board> postsByPostIdList = boardMapper.getBoardsByBoardIdList(postList);

        Map<Integer, Board> postListMapping = getPostMappingByPostIdList(postsByPostIdList);

        List<BoardResponseDto> boardResponseDtoList = getBoardResponseDtoListByPostMapping(postList, postListMapping);

        int totalPageCount = getTotalPageCount(searchConditionDto.getPageNumber());

        return BoardListResponseDto.from(boardResponseDtoList, totalPageCount);
    }

    @Override
    @Transactional
    public void deleteBoardById(int boardId, String memberEmail) {
        Board board = boardMapper.findBoardByBoardId(boardId);
        MemberResponseDto member = memberServiceClient.getMemberByMemberEmail(memberEmail);

        member.checkMemberIdNull();
        member.checkSameMemberId(board.getMemberId());

        commentServiceClient.deleteCommentByBoardId(boardId);
        boardMapper.deleteBoard(board.getBoardId());
    }

    @Override
    public boolean existBoardByBoardId(int boardId) {
        return boardMapper.checkBoardExist(boardId);
    }

    @Override
    public List<BoardResponseDto> getMyBoards(String memberEmail) {
        MemberResponseDto member = memberServiceClient.getMemberByMemberEmail(memberEmail);

        List<Board> myBoard = boardMapper.getMyBoard(member.getMemberId());

        return myBoard.stream().map(BoardResponseDto::myBoardFrom).toList();
    }

    private List<BoardResponseDto> getBoardResponseDtoListByPostMapping(List<Integer> postList, Map<Integer, Board> postListMapping) {
        List<BoardResponseDto> list = postList.stream()
                .map(boardId -> {
                    Board board = postListMapping.get(boardId);
                    return BoardResponseDto.from(board);
                }).toList();

        return mapMemberNicknamesToBoardResponseDtoList(list);
    }

    private List<BoardResponseDto> mapMemberNicknamesToBoardResponseDtoList(List<BoardResponseDto> boardResponseDtoList) {
        List<Integer> memberIdList = boardResponseDtoList.stream().map(BoardResponseDto::getMemberId).toList();
        Map<Integer, String> memberNicknamesByMemberIdList = memberServiceClient.getMemberNicknamesByMemberIdList(memberIdList);
        return boardResponseDtoList.stream()
                .peek(boardResponseDto -> {
                    String nickname = memberNicknamesByMemberIdList.get(boardResponseDto.getMemberId());

                    boardResponseDto.setMemberNickname(nickname);
                }).toList();
    }

    private Map<Integer, Board> getPostMappingByPostIdList(List<Board> postsByPostIdList) {
        return postsByPostIdList.stream()
                .collect(Collectors.toMap(Board::getBoardId, Function.identity()));
    }

    private List<Integer> getFilteredPostIdsByCondition(SearchConditionDto searchConditionDto) {
        if (searchConditionDto.isSortOrderEmpty()) {
            return boardMapper.getBoardIdsByBasic(searchConditionDto);
        }
        return boardStarMapper.getBoardIdsBySortType(searchConditionDto);
    }

    private int getTotalPageCount(int pageNumber) {
        int totalBoards = boardMapper.countTotalBoards();
        int totalPageCount = (int) Math.ceil((double) totalBoards / 5);

        if (totalPageCount < pageNumber) {
            throw new RuntimeException("입력한 페이지는 존재하지 않습니다");
        }

        return totalPageCount;
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
