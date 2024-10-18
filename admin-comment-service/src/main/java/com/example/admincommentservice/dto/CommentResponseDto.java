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
    private int adminId;
    private String memberNickname;
    private int boardId;
    private String commentContent;
    private String commentCreatedAt;
    private String commentUpdatedAt;
    private boolean commentIsDelete;

    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .adminId(comment.getAdminId())
                .boardId(comment.getBoardId())
                .commentContent(comment.getCommentContent())
                .commentCreatedAt(TimeChangerUtil.timeChange(comment.getCommentCreatedAt()))
                .commentUpdatedAt(TimeChangerUtil.timeChange(comment.getCommentUpdatedAt()))
                .commentIsDelete(comment.isCommentIsDelete())
                .build();
    }

    public static CommentResponseDto from(Comment comment, String memberNickname) {
        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .memberId(comment.getMemberId())
                .adminId(comment.getAdminId())
                .memberNickname(memberNickname)
                .boardId(comment.getBoardId())
                .commentContent(comment.getCommentContent())
                .commentCreatedAt(TimeChangerUtil.timeChange(comment.getCommentCreatedAt()))
                .commentUpdatedAt(TimeChangerUtil.timeChange(comment.getCommentUpdatedAt()))
                .commentIsDelete(comment.isCommentIsDelete())
                .build();
    }
}
