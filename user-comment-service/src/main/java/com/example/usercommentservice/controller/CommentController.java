package com.example.usercommentservice.controller;

import com.example.usercommentservice.commonApi.BodyResponse;
import com.example.usercommentservice.dto.CommentRequestDto;
import com.example.usercommentservice.dto.CommentResponseDto;
import com.example.usercommentservice.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/healthCheck")
    public ResponseEntity<BodyResponse<String>> healthCheck() {
        return ResponseEntity.status(HttpStatus.OK).body(
                BodyResponse.success("헬스 체크")
        );
    }

    @PostMapping()
    public ResponseEntity<BodyResponse<List<CommentResponseDto>>> addComment(@RequestBody @Valid CommentRequestDto commentRequestDto) {
        commentService.addComment(commentRequestDto);
        List<CommentResponseDto> comments = commentService.getCommentsByBoardId(commentRequestDto.getBoardId());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                BodyResponse.success(comments)
        );
    }
}
