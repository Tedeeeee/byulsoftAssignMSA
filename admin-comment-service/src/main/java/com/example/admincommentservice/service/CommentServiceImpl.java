package com.example.admincommentservice.service;

import com.example.admincommentservice.dto.CommentListResponseDto;
import com.example.admincommentservice.dto.CommentResponseDto;
import com.example.admincommentservice.dto.SearchConditionDto;
import com.example.admincommentservice.entity.Comment;
import com.example.admincommentservice.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    public CommentListResponseDto getAllComment(SearchConditionDto searchConditionDto) {
        int totalPageCount = getTotalPageCount();

        searchConditionDto.checkValidationPageNumber(totalPageCount);

        List<CommentResponseDto> allComment = commentMapper.getAllComment(searchConditionDto).stream()
                .map(CommentResponseDto::from)
                .toList();

        return CommentListResponseDto.from(allComment, totalPageCount);
    }

    @Override
    public void deleteCommentByCommentId(int commentId) {
        commentMapper.softDeleteComment(commentId);
    }

    private int getTotalPageCount() {
        int totalComments = commentMapper.countTotalComments();
        return (int) Math.ceil((double) totalComments / 5);
    }
}
