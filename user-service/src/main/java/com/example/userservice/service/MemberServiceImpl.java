package com.example.userservice.service;

import com.example.userservice.dto.AuthMemberDto;
import com.example.userservice.dto.MemberRequestDto;
import com.example.userservice.entity.Member;
import com.example.userservice.dto.MemberResponseDto;
import com.example.userservice.kafka.service.KafkaProducerSendService;
import com.example.userservice.mapper.MemberMapper;
import com.example.userservice.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final KafkaProducerSendService kafkaProducerSendService;

    @Override
    public MemberResponseDto findUserByMemberEmail(String memberEmail) {
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
    @Transactional
    public void registerMember(MemberRequestDto memberRequestDto) {
        memberRequestDto.memberSignupValidator();

        Member member = memberRequestDto.toEntity();

        try {
            memberMapper.save(member);
            AuthMemberDto authMember = AuthMemberDto.from(member);
            // 만약 kafka의 서버가 장애가 발생해서 처리가 안되었다면?
            // 해당 회원가입도 되지 않아야 한다.
            kafkaProducerSendService.send("register-topic", authMember);
        } catch (Exception e) {
            throw new RuntimeException("failed to save member");
        }
    }
}
