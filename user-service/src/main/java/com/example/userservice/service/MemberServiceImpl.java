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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public int findMemberIdByMemberNickname(String memberNickname) {
        return memberMapper.findMemberIdByMemberNickname(memberNickname)
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다"));
    }

    @Override
    public void checkEmail(String email) {
        ValidationUtil.emailValidationCheck(email);

        boolean checkEmail = memberMapper.checkEmail(email);
        if (checkEmail) {
            throw new RuntimeException("이미 존재하는 이메일 입니다");
        }
    }

    @Override
    public void checkNickname(String nickname) {
        ValidationUtil.nicknameValidationCheck(nickname);

        boolean checkNickName = memberMapper.checkNickName(nickname);
        if (checkNickName) {
            throw new RuntimeException("이미 존재하는 닉네임입니다");
        }
    }

    @Override
    @Transactional
    public void changeNickname(String nickname, String memberEmail) {
        Member member = memberMapper.findUserByMemberEmail(memberEmail)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다"));

        memberMapper.changeMemberNickname(nickname, member.getMemberId());
    }

    @Override
    public void checkOriginPassword(String enterPassword, String memberEmail) {
        Member member = memberMapper.findUserByMemberEmail(memberEmail)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다"));

        member.checkPassword(enterPassword);
    }

    @Override
    @Transactional
    public void changePassword(String enterPassword, String memberEmail) {
        Member member = memberMapper.findUserByMemberEmail(memberEmail)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다"));

        String newPassword = member.changePasswordEncoding(enterPassword);

        try {
            memberMapper.changeMemberPassword(newPassword, member.getMemberId());

            member.setMemberPassword(newPassword);
            AuthMemberDto authMember = AuthMemberDto.from(member);
            // 변경된 비밀번호 kafka 통신으로 옮기기 ( AuthService )
            kafkaProducerSendService.send("newPassword-topic", authMember);
        } catch (Exception e) {
            throw new RuntimeException("비밀번호 변경 실패" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void registerMember(MemberRequestDto memberRequestDto) {
        checkEmail(memberRequestDto.getMemberEmail());
        checkNickname(memberRequestDto.getMemberNickname());

        Member member = memberRequestDto.toEntity();

        try {
            memberMapper.save(member);
            AuthMemberDto authMember = AuthMemberDto.from(member);
            // 만약 kafka의 서버가 장애가 발생해서 처리가 안되었다면?
            // 해당 회원가입도 되지 않아야 한다.
            kafkaProducerSendService.send("register-topic", authMember);
        } catch (Exception e) {
            throw new RuntimeException("회원가입 실패" + e.getMessage());
        }
    }

    @Override
    public Map<Integer, String> findUserNicknamesByMemberList(List<Integer> memberIdList) {
        // 여기서 list 순서대로 가져와야 한다.
        List<Member> findMemberList = memberMapper.findMemberNicknameByMemberIdList(memberIdList);

        return findMemberList.stream()
                        .collect(Collectors.toMap(Member::getMemberId,
                                member -> Optional.ofNullable(member.getMemberNickname()).orElse("탈퇴 회원")));
    }

    @Override
    public List<MemberResponseDto> findMemberAll() {
        List<Member> memberAll = memberMapper.findMemberAll();

        return memberAll.stream()
                .map(MemberResponseDto::from).toList();
    }

    @Override
    public MemberResponseDto findMemberByMemberId(int memberId) {
        Member member = memberMapper.findMemberById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다"));

        return MemberResponseDto.from(member);
    }
}
