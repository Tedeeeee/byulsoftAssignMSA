package com.example.usercommentservice.dto;

import com.example.usercommentservice.entity.Comment;
import com.example.usercommentservice.util.TimeChangerUtil;
import lombok.*;

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
                .build();
    }

    public static CommentResponseDto myCommentFrom(Comment comment) {
        return CommentResponseDto.builder()
                .boardId(comment.getBoardId())
                .commentContent(comment.getCommentContent())
                .commentCreatedAt(TimeChangerUtil.timeChange(comment.getCommentCreatedAt()))
                .build();
    }
}
