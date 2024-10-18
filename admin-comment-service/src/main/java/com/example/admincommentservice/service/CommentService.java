package com.example.admincommentservice.service;

import com.example.admincommentservice.dto.CommentListResponseDto;
import com.example.admincommentservice.dto.SearchConditionDto;
import com.example.admincommentservice.dto.CommentRequestDto;
import com.example.admincommentservice.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {

    /**
     * 설명 : 서비스 내부의 모든 댓글
     *       특정 사용자의 댓글도 불러오기 가능
     * @author : T.S YUN
     * @since : 2024.10.08
     */
    CommentListResponseDto getAllComment(SearchConditionDto searchConditionDto);

    /**
     * 설명 : 해당 게시글의 댓글 가져오기
     * @since : 2024.10.04
     * @author : T.S YUN
     */
    List<CommentResponseDto> getCommentsByBoardId(int boardId);

    /**
     * 설명 : 댓글 저장하기
     * @since : 2024.10.04
     * @author : T.S YUN
     */
    void addComment(CommentRequestDto commentRequestDto);

    /**
     * 설명 : 댓글 수정하기
     * @since : 2024.10.07
     * @author : T.S YUN
     */
    void updateComment(CommentRequestDto commentRequestDto, String adminEmail);

    /**
     * 설명 : 댓글 강제 삭제
     *
     * @author : T.S YUN
     * @since : 2024.10.08
     */
    void deleteComment(int commentId);
}
