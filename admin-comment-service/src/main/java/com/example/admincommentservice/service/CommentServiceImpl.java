package com.example.admincommentservice.service;

import com.example.admincommentservice.client.AdminServiceClient;
import com.example.admincommentservice.client.MemberServiceClient;
import com.example.admincommentservice.dto.*;
import com.example.admincommentservice.mapper.CommentMapper;
import com.example.admincommentservice.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final MemberServiceClient memberServiceClient;
    private final AdminServiceClient adminServiceClient;

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
    @Transactional
    public void updateComment(CommentRequestDto commentRequestDto, String adminEmail) {
        commentRequestDto.validationCheck();

        AdminResponseDto admin = adminServiceClient.getAdminByAdminEmail(adminEmail);
        admin.checkSameAdminId(commentRequestDto.getAdminId());

        Comment comment = commentRequestDto.toEntity();

        commentMapper.updateComment(comment);
    }

    @Override
    @Transactional
    public void deleteComment(int commentId) {
        commentMapper.softDeleteComment(commentId);
    }

    private int getTotalPageCount() {
        int totalComments = commentMapper.countTotalComments();
        return (int) Math.ceil((double) totalComments / 5);
    }
}
