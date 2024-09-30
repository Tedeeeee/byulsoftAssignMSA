package com.example.authservice.mapper;

import com.example.authservice.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface MemberMapper {
    void save(Member member);
    Optional<Member> findMemberByMemberEmail(String memberEmail);
    void saveRefreshToken(@Param("memberId") int memberId,@Param("memberRefreshToken") String refreshToken);
}
