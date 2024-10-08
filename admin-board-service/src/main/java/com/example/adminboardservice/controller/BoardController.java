package com.example.adminboardservice.controller;

import com.example.adminboardservice.commonApi.BodyResponse;
import com.example.adminboardservice.dto.BoardListResponseDto;
import com.example.adminboardservice.dto.SearchConditionDto;
import com.example.adminboardservice.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 게시글 전체 가져오기
    @GetMapping
    public ResponseEntity<BodyResponse<BoardListResponseDto>> getAllBoards(@RequestParam(name = "searchType") String searchType,
                                                                           @RequestParam(name = "searchText") String searchText,
                                                                           @RequestParam(name = "startDate") String startDate,
                                                                           @RequestParam(name = "endDate") String endDate,
                                                                           @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                                                                           @RequestParam(name = "memberId", defaultValue = "0") int memberId) {
        SearchConditionDto searchConditionDto = new SearchConditionDto(searchType, searchText, startDate, endDate, memberId, pageNumber);
        BoardListResponseDto allBoards = boardService.getAllBoards(searchConditionDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(allBoards));
    }

    @PostMapping("/delete/{boardId}")
    public ResponseEntity<BodyResponse<String>> deleteBoardByBoardId(@PathVariable("boardId") int boardId) {
        boardService.deleteBoardByBoardId(boardId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("게시글이 삭제되었습니다"));
    }
}
