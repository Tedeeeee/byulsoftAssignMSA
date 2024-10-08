package com.example.admincommentservice.controller;

import com.example.admincommentservice.commonApi.BodyResponse;
import com.example.admincommentservice.dto.CommentListResponseDto;
import com.example.admincommentservice.dto.SearchConditionDto;
import com.example.admincommentservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<BodyResponse<CommentListResponseDto>> getAllComments(@RequestParam(name = "startDate") String startDate,
                                                                               @RequestParam(name = "endDate") String endDate,
                                                                               @RequestParam(name = "memberId", defaultValue = "0") int memberId,
                                                                               @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber) {
        SearchConditionDto searchConditionDto = new SearchConditionDto(startDate, endDate, memberId, pageNumber);
        CommentListResponseDto allComment = commentService.getAllComment(searchConditionDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(allComment));
    }

    @PostMapping("/delete/{commentId}")
    public ResponseEntity<BodyResponse<String>> deleteComment(@PathVariable("commentId") int commentId) {
        commentService.deleteCommentByCommentId(commentId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("댓글이 삭제되었습니다"));
    }
}
