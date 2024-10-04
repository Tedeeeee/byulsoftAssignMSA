package com.example.usercommentservice.service;

import com.example.usercommentservice.dto.CommentRequestDto;
import com.example.usercommentservice.dto.CommentResponseDto;
import com.example.usercommentservice.entity.Comment;
import com.example.usercommentservice.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    public void addComment(CommentRequestDto commentRequestDto) {
        commentRequestDto.validationCheck();

        Comment comment = commentRequestDto.toEntity();
        try {
            commentMapper.save(comment);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<CommentResponseDto> getCommentsByBoardId(int boardId) {
        List<Comment> comments = commentMapper.findCommentsByBoardId(boardId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        return comments.stream().map(CommentResponseDto::from).toList();
    }
}
