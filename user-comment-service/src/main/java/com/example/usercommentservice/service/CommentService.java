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
    void addComment(CommentRequestDto commentRequestDto);
}