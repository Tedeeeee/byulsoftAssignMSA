package com.example.userservice.mapper;

import com.example.userservice.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {
    int save(Member member);
    Optional<Member> findUserByMemberEmail(String memberEmail);
    Optional<String> findUserNicknameByMemberId(int memberId);
    boolean checkNickName(String memberNickName);
    void changeMemberNickname(@Param("memberNickname") String memberNickName, @Param("memberId") int memberId);
    void changeMemberPassword(@Param("memberPassword") String memberPassword, @Param("memberId") int memberId);
    boolean checkEmail(String memberEmail);
    List<Member> findMemberNicknameByMemberIdList(List<Integer> memberIdList);
    Optional<Member> findMemberIdByMemberNickname(String memberNickname);
    List<Member> findMemberAll();
    Optional<Member> findMemberById(int memberId);
    List<Member> findMembersByMemberNickname(String memberNickname);
}
