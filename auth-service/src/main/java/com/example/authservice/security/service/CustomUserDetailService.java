package com.example.authservice.security.service;

import com.example.authservice.entity.Member;
import com.example.authservice.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberMapper.findMemberByMemberEmail(username)
                .orElseThrow(() -> new RuntimeException("사용자의 정보가 존재하지 않습니다"));

        member.checkIfMemberIsDeleted();

        return User.builder()
                .username(member.getMemberEmail())
                .password(member.getMemberPassword())
                .build();
    }
}
