package com.example.userservice.mapper;

import com.example.userservice.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {
    Optional<Member> findMemberByMemberEmail(String memberEmail);
}
