package com.example.userservice.service;

import com.example.userservice.entity.Member;
import com.example.userservice.dto.MemberResponseDto;
import com.example.userservice.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Override
    public MemberResponseDto findUserByMemberEmail(String memberEmail) {

        Member member = memberMapper.findMemberByMemberEmail(memberEmail)
                .orElseThrow(() -> new RuntimeException("사용자가 확인되지 않습니다."));

        return MemberResponseDto.from(member);
    }
}
