package com.example.usercommentservice.dto;

import com.example.usercommentservice.entity.Comment;
import com.example.usercommentservice.util.ValidationUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private int commentId;
    @NotNull
    private int memberId;

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
                .memberId(memberId)
                .boardId(boardId)
                .commentContent(commentContent)
                .build();
    }
}
