package com.example.userservice.service;

import com.example.userservice.dto.MemberRequestDto;
import com.example.userservice.entity.Member;
import com.example.userservice.dto.MemberResponseDto;
import com.example.userservice.mapper.MemberMapper;
import com.example.userservice.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Override
    public MemberResponseDto findUserByMemberEmail(String memberEmail) {

        System.out.println(memberEmail);
        Member member = memberMapper.findUserByMemberEmail(memberEmail)
                .orElseThrow(() -> new RuntimeException("사용자가 확인되지 않습니다."));

        return MemberResponseDto.from(member);
    }

    @Override
    public String findUserNicknameByMemberId(int memberId) {
        String nickname = memberMapper.findUserNicknameByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("사용자가 확인되지 않습니다"));

        if (nickname == null || nickname.isEmpty()) {
            nickname = "탈퇴한 회원입니다";
        }

        return nickname;
    }

    @Override
    public void checkEmail(String email) {
        ValidationUtil.emailValidationCheck(email);

        boolean checkEmail = memberMapper.checkEmail(email);
        if (checkEmail) {
            throw new RuntimeException("email already in use");
        }
    }

    @Override
    public void checkNickname(String nickname) {
        ValidationUtil.nicknameValidationCheck(nickname);

        boolean checkNickName = memberMapper.checkNickName(nickname);
        if (checkNickName) {
            throw new RuntimeException("nickname already in use");
        }
    }

    @Override
    public void registerMember(MemberRequestDto memberRequestDto) {
        // 중복 검사에서는 nickname 자체를 검사하는데 여기선 memberRequestDto 가 검사를 한다.
        // 어떻게 하면 좋을까?
        memberRequestDto.memberSignupValidator();

        Member member = memberRequestDto.toEntity();

        try {
            memberMapper.save(member);
        } catch (Exception e) {
            throw new RuntimeException("failed to save member");
        }
    }
}
