package com.example.usercommentservice.service;

import com.example.usercommentservice.client.BoardServiceClient;
import com.example.usercommentservice.client.MemberServiceClient;
import com.example.usercommentservice.dto.CommentRequestDto;
import com.example.usercommentservice.dto.CommentResponseDto;
import com.example.usercommentservice.dto.MemberResponseDto;
import com.example.usercommentservice.entity.Comment;
import com.example.usercommentservice.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final MemberServiceClient memberServiceClient;
    private final BoardServiceClient boardServiceClient;

    @Override
    @Transactional
    public void addComment(CommentRequestDto commentRequestDto, String memberEmail) {
        commentRequestDto.validationCheck();

        // boardId를 검색하면 자연스럽게 member도 검증
        if (!boardServiceClient.getBoardByBoardId(commentRequestDto.getBoardId())) {
            throw new RuntimeException("Board does not exist");
        }

        Comment comment = commentRequestDto.toEntity();
        try {
            commentMapper.save(comment);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<CommentResponseDto> getCommentsByBoardId(int boardId) {
        List<Comment> comments = commentMapper.findCommentsByBoardId(boardId);

        if (comments.isEmpty()) {
            throw new RuntimeException("No comments found");
        }

        return comments.stream().map(CommentResponseDto::from).toList();
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto, String memberEmail) {
        commentRequestDto.validationCheck();

        MemberResponseDto member = memberServiceClient.getMemberByMemberEmail(memberEmail);
        member.checkSameMemberId(commentRequestDto.getMemberId());

        Comment comment = commentRequestDto.toEntity();

        commentMapper.updateComment(comment);
        Comment renewComment = commentMapper.findCommentByCommentId(comment.getCommentId())
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        return CommentResponseDto.from(renewComment);
    }

    @Override
    @Transactional
    public void deleteComment(int commentId, String memberEmail) {
        MemberResponseDto member = memberServiceClient.getMemberByMemberEmail(memberEmail);

        Comment comment = commentMapper.findCommentByCommentId(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        member.checkSameMemberId(comment.getMemberId());

        commentMapper.deleteCommentByCommentId(commentId);
    }

    // 여기서 카프카 활용 가능
    @Override
    @Transactional
    public void deleteAllCommentsByBoardId(int boardId) {
        commentMapper.deleteCommentByBoardId(boardId);
    }
}
