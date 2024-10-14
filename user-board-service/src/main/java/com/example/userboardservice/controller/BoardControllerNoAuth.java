package com.example.userboardservice.controller;

import com.example.userboardservice.commonApi.BodyResponse;
import com.example.userboardservice.dto.BoardListResponseDto;
import com.example.userboardservice.dto.BoardResponseDto;
import com.example.userboardservice.dto.SearchConditionDto;
import com.example.userboardservice.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/noAuth/boards")
public class BoardControllerNoAuth {
    private final BoardService boardService;


    @GetMapping("")
    public ResponseEntity<BodyResponse<BoardListResponseDto>> getBoards(@RequestParam(name = "searchType") String searchType,
                                                                        @RequestParam(name = "searchText") String searchText,
                                                                        @RequestParam(name = "sortOrder") String sortOrder,
                                                                        @RequestParam(name = "sortType") String sortType,
                                                                        @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber) {
        SearchConditionDto searchConditionDto = new SearchConditionDto(sortOrder, sortType, searchType, searchText, pageNumber);
        BoardListResponseDto postsByCondition = boardService.getPostsByCondition(searchConditionDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(postsByCondition));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BodyResponse<BoardResponseDto>> getBoard(@PathVariable int boardId) {
        BoardResponseDto boardResponseDto = boardService.findBoardById(boardId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(boardResponseDto));
    }


    // feign 요청들
    @GetMapping("/feign/{boardId}")
    public boolean getBoardByBoardId(@PathVariable int boardId) {
        // 존재 유무만 확인
        return boardService.existBoardByBoardId(boardId);
    }

    @GetMapping("/feign/checkMemberId")
    public boolean selfReportByMemberId() {
        return true;
    }
}
