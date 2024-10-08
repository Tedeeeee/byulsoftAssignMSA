package com.example.admincommentservice.mapper;

import com.example.admincommentservice.dto.CommentResponseDto;
import com.example.admincommentservice.dto.SearchConditionDto;
import com.example.admincommentservice.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> getAllComment(SearchConditionDto searchConditionDto);
    int countTotalComments();
    void softDeleteComment(int commentId);
}
