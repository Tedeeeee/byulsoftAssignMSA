package com.example.userboardservice.controller;

import com.example.userboardservice.commonApi.BodyResponse;
import com.example.userboardservice.dto.BoardRequestDto;
import com.example.userboardservice.dto.BoardResponseDto;
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
                .body(BodyResponse.success("게시글이 등록되었습니다")
        );
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

    @GetMapping
    public ResponseEntity<BodyResponse<List<BoardResponseDto>>> getMyBoards(HttpServletRequest request) {
        String memberEmail = request.getHeader("memberEmail");
        List<BoardResponseDto> myBoards = boardService.getMyBoards(memberEmail);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(myBoards));
    }
}
