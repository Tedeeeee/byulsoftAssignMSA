package com.example.usercommentservice.dto;

import com.example.usercommentservice.entity.Comment;
import com.example.usercommentservice.util.ValidationUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {


    @NotBlank
    private int memberId;

    @NotBlank
    private int boardId;

    @NotBlank
    private String commentContent;
    private String commentCreatedAt;
    private String commentUpdatedAt;

    public void validationCheck() {
        // 여기서 boardId, memberId 모두 확인 ( openFeign )

        // 댓글 content 의 validation 확인
        ValidationUtil.checkContentValidate(commentContent);
    }

    public Comment toEntity() {
        return Comment.builder()
                .memberId(this.memberId)
                .boardId(this.boardId)
                .commentContent(this.commentContent)
                .build();
    }
}
