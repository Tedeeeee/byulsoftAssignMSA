package com.example.authservice.mapper;

import com.example.authservice.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface MemberMapper {
    void save(Member member);
    void saveRefreshToken(@Param("memberId") int memberId,@Param("memberRefreshToken") String refreshToken);
    Member findMemberByMemberEmail(String memberEmail);
    Optional<Member> findMemberByRefreshToken(String refreshToken);
}
