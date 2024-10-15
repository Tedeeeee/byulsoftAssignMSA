package com.example.usercommentservice.mapper;

import com.example.usercommentservice.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {
    void save(Comment comment);
    List<Comment> findCommentsByBoardId(int boardId);
    void updateComment(Comment comment);
    Optional<Comment> findCommentByCommentId(int commentId);
    void deleteCommentByCommentId(int commentId);
    void deleteCommentByBoardId(int boardId);
    List<Comment> getMyComment(int memberId);
}
