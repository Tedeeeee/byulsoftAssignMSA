package com.example.usercommentservice.mapper;

import com.example.usercommentservice.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {
    void save(Comment comment);
    Optional<List<Comment>> findCommentsByBoardId(int boardId);
}
