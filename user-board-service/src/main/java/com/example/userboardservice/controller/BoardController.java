package com.example.userboardservice.controller;

import com.example.userboardservice.commonApi.BodyResponse;
import com.example.userboardservice.dto.BoardListResponseDto;
import com.example.userboardservice.dto.BoardRequestDto;
import com.example.userboardservice.dto.BoardResponseDto;
import com.example.userboardservice.dto.SearchConditionDto;
import com.example.userboardservice.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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

    @PostMapping("")
    public ResponseEntity<BodyResponse<String>> addBoard(@RequestBody @Valid BoardRequestDto boardRequestDto, HttpServletRequest request) {
        String memberEmail = request.getHeader("memberEmail");
        boardService.addBoard(boardRequestDto, memberEmail);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BodyResponse.createSuccess("게시글이 등록되었습니다")
        );
    }

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

    @PostMapping("/update")
    public ResponseEntity<BodyResponse<BoardResponseDto>> updateBoard(@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        String memberEmail = request.getHeader("memberEmail");
        boardService.updateBoard(boardRequestDto, memberEmail);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("게시글이 수정되었습니다"));
    }

    @PostMapping("/delete/{boardId}")
    public ResponseEntity<BodyResponse<String>> deleteBoard(@PathVariable int boardId, HttpServletRequest request) {
        String memberEmail = request.getHeader("memberEmail");
        boardService.deleteBoardById(boardId, memberEmail);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("삭제가 되었습니다"));
    }


}
