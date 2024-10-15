package com.example.authservice.mapper;

import com.example.authservice.entity.Member;
import com.example.authservice.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface MemberMapper {
    void save(Member member);
    void saveRefreshToken(@Param("memberOriginalId") int memberOriginalId, @Param("memberRole") Role memberRole, @Param("memberRefreshToken") String refreshToken);
    Optional<Member> findMemberByMemberEmailForUser(String memberEmail);
    Optional<Member> findMemberByMemberEmailForAdmin(String memberEmail);
    Optional<Member> findMemberByRefreshToken(String refreshToken);
    void logout(Member member);
    void changePassword(Member member);
}
