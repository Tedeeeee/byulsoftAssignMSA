package com.example.userboardservice.controller;

import com.example.userboardservice.dto.BoardResponseDto;
import com.example.userboardservice.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feign")
public class FeignController {
    private final BoardService boardService;

    @GetMapping("/boards/{boardId}")
    public boolean getBoardByBoardId(@PathVariable int boardId) {
        // 존재 유무만 확인
        return boardService.existBoardByBoardId(boardId);
    }
}
