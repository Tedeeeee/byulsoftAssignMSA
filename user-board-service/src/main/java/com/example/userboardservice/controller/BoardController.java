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

    @GetMapping("{id}")
    public ResponseEntity<BodyResponse<BoardResponseDto>> getBoard(@PathVariable int boardId, HttpServletRequest request) {
        String memberEmail = request.getHeader("memberEmail");
        BoardResponseDto boardResponseDto = boardService.findBoardById(boardId, memberEmail);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(boardResponseDto));
    }
}
