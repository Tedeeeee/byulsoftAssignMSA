package com.example.admincommentservice.dto;

import com.example.admincommentservice.entity.Comment;
import com.example.admincommentservice.util.TimeChangerUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {
    private int commentId;
    private int memberId;
    private int boardId;
    private String commentContent;
    private String commentCreatedAt;
    private String commentUpdatedAt;
    private boolean commentIsDelete;

    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .memberId(comment.getMemberId())
                .boardId(comment.getBoardId())
                .commentContent(comment.getCommentContent())
                .commentCreatedAt(TimeChangerUtil.timeChange(comment.getCommentCreatedAt()))
                .commentUpdatedAt(TimeChangerUtil.timeChange(comment.getCommentUpdatedAt()))
                .commentIsDelete(comment.isCommentIsDelete())
                .build();
    }
}
