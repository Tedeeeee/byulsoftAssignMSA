package com.example.usercommentservice.controller;

import com.example.usercommentservice.commonApi.BodyResponse;
import com.example.usercommentservice.dto.CommentResponseDto;
import com.example.usercommentservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/noAuth/comments")
public class CommentControllerNoAuth {

    private final CommentService commentService;

    // 게시글의 댓글 가져오기
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<BodyResponse<List<CommentResponseDto>>> getAllComments(@PathVariable int boardId) {
        List<CommentResponseDto> comments = commentService.getCommentsByBoardId(boardId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                BodyResponse.success(comments));
    }

    // feign 요청
    @PostMapping("/boards/{boardId}")
    void deleteCommentByBoardId(@PathVariable int boardId) {
        commentService.deleteAllCommentsByBoardId(boardId);
    }
}
