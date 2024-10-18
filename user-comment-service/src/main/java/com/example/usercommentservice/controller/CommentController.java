package com.example.usercommentservice.controller;

import com.example.usercommentservice.commonApi.BodyResponse;
import com.example.usercommentservice.dto.CommentRequestDto;
import com.example.usercommentservice.dto.CommentResponseDto;
import com.example.usercommentservice.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<BodyResponse<List<CommentResponseDto>>> addComment(@RequestBody @Valid CommentRequestDto commentRequestDto,
                                                                             HttpServletRequest request) {
        String memberEmail = request.getHeader("memberEmail");
        commentService.addComment(commentRequestDto, memberEmail);
        List<CommentResponseDto> comments = commentService.getCommentsByBoardId(commentRequestDto.getBoardId());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                BodyResponse.success(comments, "댓글이 등록되었습니다")
        );
    }

    @PostMapping("/update")
    public ResponseEntity<BodyResponse<List<CommentResponseDto>>> updateComment(@RequestBody @Valid CommentRequestDto commentRequestDto,
                                                              HttpServletRequest request) {
        String memberEmail = request.getHeader("memberEmail");
        commentService.updateComment(commentRequestDto, memberEmail);
        List<CommentResponseDto> commentsByBoardId = commentService.getCommentsByBoardId(commentRequestDto.getBoardId());

        return ResponseEntity.status(HttpStatus.OK).body(
                BodyResponse.success(commentsByBoardId, "댓글이 수정되었습니다"));
    }

    @PostMapping("/delete/{commentId}")
    public ResponseEntity<BodyResponse<String>> deleteComment(@PathVariable int commentId, HttpServletRequest request) {
        String memberEmail = request.getHeader("memberEmail");
        commentService.deleteComment(commentId, memberEmail);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("댓글이 삭제되었습니다"));
    }

    @GetMapping
    public ResponseEntity<BodyResponse<List<CommentResponseDto>>> getComments(HttpServletRequest request) {
        String memberEmail = request.getHeader("memberEmail");
        List<CommentResponseDto> myComments = commentService.getMyComments(memberEmail);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(myComments));
    }
}
