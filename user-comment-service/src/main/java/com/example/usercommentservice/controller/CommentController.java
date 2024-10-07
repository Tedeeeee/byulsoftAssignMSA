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

    // 댓글 등록
    @PostMapping()
    public ResponseEntity<BodyResponse<List<CommentResponseDto>>> addComment(@RequestBody @Valid CommentRequestDto commentRequestDto,
                                                                             HttpServletRequest request) {
        String memberEmail = request.getHeader("memberEmail");
        commentService.addComment(commentRequestDto, memberEmail);
        List<CommentResponseDto> comments = commentService.getCommentsByBoardId(commentRequestDto.getBoardId());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                BodyResponse.success(comments)
        );
    }

    // 게시글의 댓글 가져오기
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<BodyResponse<List<CommentResponseDto>>> getAllComments(@PathVariable int boardId) {
        List<CommentResponseDto> comments = commentService.getCommentsByBoardId(boardId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                BodyResponse.success(comments));
    }

    @PostMapping("/update")
    public ResponseEntity<BodyResponse<CommentResponseDto>> updateComment(@RequestBody @Valid CommentRequestDto commentRequestDto,
                                                              HttpServletRequest request) {
        String memberEmail = request.getHeader("memberEmail");
        CommentResponseDto commentResponseDto = commentService.updateComment(commentRequestDto, memberEmail);

        return ResponseEntity.status(HttpStatus.OK).body(
                BodyResponse.success(commentResponseDto));
    }

    @PostMapping("/delete/{commentId}")
    public ResponseEntity<BodyResponse<String>> deleteComment(@PathVariable int commentId, HttpServletRequest request) {
        String memberEmail = request.getHeader("memberEmail");
        commentService.deleteComment(commentId, memberEmail);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("댓글이 삭제되었습니다"));
    }
}
