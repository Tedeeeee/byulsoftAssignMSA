package com.example.userboardservice.service;

import com.example.userboardservice.client.CommentServiceClient;
import com.example.userboardservice.client.MemberServiceClient;
import com.example.userboardservice.dto.*;
import com.example.userboardservice.entity.Board;
import com.example.userboardservice.entity.BoardStar;
import com.example.userboardservice.mapper.BoardMapper;
import com.example.userboardservice.mapper.BoardStarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;
    private final BoardStarMapper boardStarMapper;
    private final MemberServiceClient memberServiceClient;
    private final CommentServiceClient commentServiceClient;

    @Override
    @Transactional
    public void addBoard(BoardRequestDto boardRequestDto, String memberEmail) {
        // 사용자의 정보 가져오기 ( openFeign )
        MemberResponseDto member = memberServiceClient.getMemberByMemberEmail(memberEmail);

        member.checkMemberIdNull();

        // 가져온 정보 validation 진행
        boardRequestDto.validationCheck();

        Board board = boardRequestDto.toEntity(member.getMemberId());
        boardMapper.save(board);

        bulkInsertBoardStars(boardRequestDto.getBoardStars(), board.getBoardId());
    }

    @Override
    public BoardResponseDto findBoardById(int boardId) {
        Board board = boardMapper.findBoardByBoardId(boardId);

        // board의 id를 통해 사용자 정보 가져오기
        String nickname = memberServiceClient.getMemberNicknameByMemberId(board.getMemberId());

        // 사용자 정보 가져와서 memberNickname 셋팅
        return BoardResponseDto.from(board, nickname);
    }

    @Override
    @Transactional
    public void updateBoard(BoardRequestDto boardRequestDto, String memberEmail) {
        // 사용자 확인 및 유효성 체크
        MemberResponseDto member = memberServiceClient.getMemberByMemberEmail(memberEmail);

        member.checkSameMemberId(boardRequestDto.getMemberId());
        boardRequestDto.validationCheck();

        // 게시글 수정
        Board board = boardRequestDto.toEntity(member.getMemberId());

        boardMapper.updateBoard(board);

        // board에 해당된 boarStar는 모두 지운다
        boardStarMapper.deleteBoardStarByBoardId(board.getBoardId());

        // 이후 별점을 다시 저장한다.
        bulkInsertBoardStars(boardRequestDto.getBoardStars(), board.getBoardId());
    }

    @Override
    public BoardListResponseDto getPostsByCondition(SearchConditionDto searchConditionDto) {
        List<Integer> postList = getFilteredPostIdsByCondition(searchConditionDto);

        List<Board> postsByPostIdList = boardMapper.getBoardsByBoardIdList(postList);

        Map<Integer, Board> postListMapping = getPostMappingByPostIdList(postsByPostIdList);

        List<BoardResponseDto> boardResponseDtoList = getBoardResponseDtoListByPostMapping(postList, postListMapping);

        int totalPageCount = getTotalPageCount(searchConditionDto.getPageNumber());

        return BoardListResponseDto.from(boardResponseDtoList, totalPageCount);
    }

    @Override
    @Transactional
    public void deleteBoardById(int boardId, String memberEmail) {
        // boardId를 가지고 정보를 가져와야 한다
        Board board = boardMapper.findBoardByBoardId(boardId);
        // 삭제하려는 자와 게시글 소유자가 맞는지 확인
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

    private List<BoardResponseDto> getBoardResponseDtoListByPostMapping(List<Integer> postList, Map<Integer, Board> postListMapping) {
        return postList.stream()
                .map(boardId -> {
                    Board board = postListMapping.get(boardId);
                    // 사용자 닉네임 가져오기
                    String nickname = memberServiceClient.getMemberNicknameByMemberId(board.getMemberId());

                    return BoardResponseDto.from(board, nickname);
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
