package com.example.userservice.service;

import com.example.userservice.client.AuthServiceClient;
import com.example.userservice.dto.AuthMemberDto;
import com.example.userservice.dto.MemberRequestDto;
import com.example.userservice.entity.Member;
import com.example.userservice.dto.MemberResponseDto;
import com.example.userservice.mapper.MemberMapper;
import com.example.userservice.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final AuthServiceClient authServiceClient;

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

        if (!StringUtils.hasText(nickname)) {
            nickname = "익명";
        }

        return nickname;
    }

    @Override
    public MemberResponseDto findMemberByMemberNickname(String memberNickname) {
        Member member = memberMapper.findMemberIdByMemberNickname(memberNickname)
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다"));

        return MemberResponseDto.from(member);
    }

    @Override
    public void checkEmail(String email) {
        ValidationUtil.emailValidationCheck(email);

        boolean checkEmail = memberMapper.checkEmail(email);
        if (checkEmail) {
            throw new RuntimeException("이미 존재하는 이메일입니다");
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

        memberMapper.changeMemberPassword(newPassword, member.getMemberId());

        member.setMemberPassword(newPassword);
        AuthMemberDto authMember = AuthMemberDto.from(member);
        authServiceClient.changePassword(authMember);
    }

    @Override
    @Transactional
    public void registerMember(MemberRequestDto memberRequestDto) {
        checkEmail(memberRequestDto.getMemberEmail());
        checkNickname(memberRequestDto.getMemberNickname());

        Member member = memberRequestDto.toEntity();

        memberMapper.save(member);

        AuthMemberDto authMember = AuthMemberDto.from(member);
        authServiceClient.memberDataSave(authMember);
    }

    @Override
    public Map<Integer, String> findUserNicknamesByMemberList(List<Integer> memberIdList) {
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

    @Override
    public List<MemberResponseDto> findMembersByMemberEmail(String memberEmail) {
        List<Member> memberList = memberMapper.findMembersByMemberNickname(memberEmail);

        return memberList.stream()
                .map(MemberResponseDto::from).toList();
    }
}
