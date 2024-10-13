package com.example.userservice.mapper;

import com.example.userservice.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    int save(Member member);
    Optional<Member> findUserByMemberEmail(String memberEmail);
    Optional<String> findUserNicknameByMemberId(int memberId);
    boolean checkNickName(String memberNickName);
    boolean checkEmail(String memberEmail);
    List<Member> findMemberNicknameByMemberIdList(List<Integer> memberIdList);
}
