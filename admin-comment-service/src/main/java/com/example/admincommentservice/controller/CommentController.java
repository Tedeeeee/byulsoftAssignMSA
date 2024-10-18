package com.example.admincommentservice.controller;

import com.example.admincommentservice.commonApi.BodyResponse;
import com.example.admincommentservice.dto.CommentListResponseDto;
import com.example.admincommentservice.dto.CommentRequestDto;
import com.example.admincommentservice.dto.CommentResponseDto;
import com.example.admincommentservice.dto.SearchConditionDto;
import com.example.admincommentservice.service.CommentService;
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

    @GetMapping
    public ResponseEntity<BodyResponse<CommentListResponseDto>> getAllComments(@RequestParam(name = "searchText") String searchText,
                                                                                @RequestParam(name = "startDate") String startDate,
                                                                               @RequestParam(name = "endDate") String endDate,
                                                                               @RequestParam(name = "memberId", defaultValue = "0") int memberId,
                                                                               @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber) {
        SearchConditionDto searchConditionDto = new SearchConditionDto(searchText, startDate, endDate, memberId, pageNumber);
        CommentListResponseDto allComment = commentService.getAllComment(searchConditionDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(allComment));
    }

    @GetMapping("/boards/{boardId}")
    public ResponseEntity<BodyResponse<List<CommentResponseDto>>> getAllComments(@PathVariable int boardId) {
        List<CommentResponseDto> comments = commentService.getCommentsByBoardId(boardId);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                BodyResponse.success(comments));
    }

    @PostMapping
    public ResponseEntity<BodyResponse<List<CommentResponseDto>>> addComment(@RequestBody @Valid CommentRequestDto commentRequestDto) {
        commentService.addComment(commentRequestDto);
        List<CommentResponseDto> comments = commentService.getCommentsByBoardId(commentRequestDto.getBoardId());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                BodyResponse.success(comments, "댓글이 등록되었습니다")
        );
    }

    @PostMapping("/update")
    public ResponseEntity<BodyResponse<List<CommentResponseDto>>> updateComment(@RequestBody @Valid CommentRequestDto commentRequestDto,
                                                                                HttpServletRequest request) {
        String adminEmail = request.getHeader("adminEmail");
        commentService.updateComment(commentRequestDto, adminEmail);
        List<CommentResponseDto> commentsByBoardId = commentService.getCommentsByBoardId(commentRequestDto.getBoardId());

        return ResponseEntity.status(HttpStatus.OK).body(
                BodyResponse.success(commentsByBoardId, "댓글이 수정되었습니다"));
    }


    @PostMapping("/delete/{commentId}")
    public ResponseEntity<BodyResponse<String>> deleteComment(@PathVariable("commentId") int commentId) {
        commentService.deleteComment(commentId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("댓글이 삭제되었습니다"));
    }
}
