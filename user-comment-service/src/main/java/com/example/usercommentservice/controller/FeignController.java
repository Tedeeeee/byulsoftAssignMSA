package com.example.usercommentservice.controller;

import com.example.usercommentservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feign")
public class FeignController {

    private final CommentService commentService;

    @PostMapping("/boards/{boardId}")
    void deleteCommentByBoardId(@PathVariable int boardId) {
        commentService.deleteAllCommentsByBoardId(boardId);
    }
}
