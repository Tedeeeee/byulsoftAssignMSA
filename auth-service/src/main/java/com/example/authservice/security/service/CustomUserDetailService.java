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
        Member member = memberMapper.findMemberByMemberEmail(username);

        if (member.isMemberIsDelete()) {
            throw new UsernameNotFoundException("탈퇴한 사용자 입니다");
        }

        return User.builder()
                .username(member.getMemberEmail())
                .password(member.getMemberPassword())
                .build();
    }
}
