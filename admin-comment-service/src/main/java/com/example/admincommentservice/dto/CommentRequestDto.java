package com.example.admincommentservice.dto;

import com.example.admincommentservice.entity.Comment;
import com.example.admincommentservice.util.ValidationUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private int commentId;
    @NotNull
    private int adminId;

    @NotNull
    private int boardId;
    @NotBlank
    private String commentContent;

    public void validationCheck() {
        // 댓글 content 의 validation 확인
        ValidationUtil.checkContentValidate(commentContent);
    }

    public Comment toEntity() {
        return Comment.builder()
                .commentId(commentId)
                .adminId(adminId)
                .boardId(boardId)
                .commentContent(commentContent)
                .build();
    }
}
