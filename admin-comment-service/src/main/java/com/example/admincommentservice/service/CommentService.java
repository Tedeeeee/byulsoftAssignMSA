package com.example.admincommentservice.service;

import com.example.admincommentservice.dto.CommentListResponseDto;
import com.example.admincommentservice.dto.SearchConditionDto;

public interface CommentService {

    /**
     * 설명 : 서비스 내부의 모든 댓글
     *       특정 사용자의 댓글도 불러오기 가능
     * @author : T.S YUN
     * @since : 2024.10.08
     */
    CommentListResponseDto getAllComment(SearchConditionDto searchConditionDto);

    /**
     * 설명 : 댓글 강제 삭제
     *
     * @author : T.S YUN
     * @since : 2024.10.08
     */
    void deleteCommentByCommentId(int commentId);
}
