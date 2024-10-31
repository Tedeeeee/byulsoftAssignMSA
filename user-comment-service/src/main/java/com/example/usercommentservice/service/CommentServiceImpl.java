package com.example.usercommentservice.service;

import com.example.usercommentservice.client.AdminServiceClient;
import com.example.usercommentservice.client.BoardServiceClient;
import com.example.usercommentservice.client.MemberServiceClient;
import com.example.usercommentservice.dto.CommentRequestDto;
import com.example.usercommentservice.dto.CommentResponseDto;
import com.example.usercommentservice.dto.MemberResponseDto;
import com.example.usercommentservice.entity.Comment;
import com.example.usercommentservice.mapper.CommentMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final MemberServiceClient memberServiceClient;
    private final BoardServiceClient boardServiceClient;
    private final AdminServiceClient adminServiceClient;

    @Override
    @Transactional
    public void addComment(CommentRequestDto commentRequestDto, String memberEmail) {
        commentRequestDto.validationCheck();

        // boardId를 검색하면 자연스럽게 member도 검증
        if (!boardServiceClient.getBoardByBoardId(commentRequestDto.getBoardId())) {
            throw new RuntimeException("해당 게시글은 존재하지 않습니다.");
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

        if (!comments.isEmpty()) {
            // 데이터를 가져올때 사용자의 닉네임을 클라이언트를 통해 가져와야한다.
            List<Integer> adminList = comments.stream().map(Comment::getAdminId).toList();
            List<Integer> memberList = comments.stream().map(Comment::getMemberId).toList();

            Map<Integer, String> adminNicknamesByAdminIdList = adminServiceClient.getAdminNicknamesByAdminIdList(adminList);
            Map<Integer, String> memberNicknamesByMemberIdList = memberServiceClient.getMemberNicknamesByMemberIdList(memberList);

            return comments.stream()
                    .map(comment -> {
                        if (comment.getMemberId() != 0) {
                            return CommentResponseDto.from(comment, memberNicknamesByMemberIdList.get(comment.getMemberId()));
                        }
                        return CommentResponseDto.from(comment, adminNicknamesByAdminIdList.get(comment.getAdminId()));
                    }).toList();
        }
        return null;
    }

    @Override
    @Transactional
    public void updateComment(CommentRequestDto commentRequestDto, String memberEmail) {
        MemberResponseDto member = memberServiceClient.getMemberByMemberEmail(memberEmail);
        commentRequestDto.validationCheck();

        member.checkSameMemberId(commentRequestDto.getMemberId());

        Comment comment = commentRequestDto.toEntity();

        commentMapper.updateComment(comment);
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

    @Override
    @Transactional
    public void deleteAllCommentsByBoardId(int boardId) {
        commentMapper.deleteCommentByBoardId(boardId);
    }

    @Override
    public List<CommentResponseDto> getMyComments(String memberEmail) {
        MemberResponseDto member = memberServiceClient.getMemberByMemberEmail(memberEmail);

        List<Comment> myComment = commentMapper.getMyComment(member.getMemberId());

        return myComment.stream()
                .map(CommentResponseDto::myCommentFrom).toList();
    }
}
