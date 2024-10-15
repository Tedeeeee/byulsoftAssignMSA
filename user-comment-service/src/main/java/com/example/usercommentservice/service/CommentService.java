package com.example.usercommentservice.service;

import com.example.usercommentservice.dto.CommentRequestDto;
import com.example.usercommentservice.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {

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
    void addComment(CommentRequestDto commentRequestDto, String memberEmail);

    /**
     * 설명 : 댓글 수정하기
     * @since : 2024.10.07
     * @author : T.S YUN
     */
    void updateComment(CommentRequestDto commentRequestDto, String memberEmail);

    /**
     * 설명 : 댓글 삭제 (soft)
     * @since : 2024.10.07
     * @author : T.S YUN
     */
    void deleteComment(int commentId, String memberEmail);

    /**
     * 설명 : 게시글 삭제로 인한 댓글 전체 삭제
     *       Feign 을 이용한 Service
     * @since : 2024.10.07
     * @author : T.S YUN
     */
    void deleteAllCommentsByBoardId(int boardId);

    /**
     * 설명 : 내가 작성한 댓글 가져오기
     * @since : 2024.10.15
     * @author : T.S YUN
     */
    List<CommentResponseDto> getMyComments(String memberEmail);
}
