package com.example.userservice.service;

import com.example.userservice.dto.MemberRequestDto;
import com.example.userservice.dto.MemberResponseDto;

import java.util.List;
import java.util.Map;

public interface MemberService {

    /**
     * 설명 : memberId를 통해 사용자의 정보를 가져온다.
     * @since : 2024.10.02
     * @author : T.S YUN
     * @return MemberResponseDto
     */
    MemberResponseDto findUserByMemberEmail(String memberEmail);

    /**
     * 설명 : id를 통해 사용자 닉네임 가져오기
     * @since : 2024.10.07
     * @author : T.S YUN
     */
    String findUserNicknameByMemberId(int memberId);

    /**
     * 설명 : 닉네임을 통해 사용자 Id 가져오기
     * @since : 2024.10.07
     * @author : T.S YUN
     */
    int findMemberIdByMemberNickname(String memberNickname);

    /**
     * 설명 : 이메일 유효성 검사와 중복 체크
     * @since : 2024.10.04
     * @author : T.S YUN
     */
    void checkEmail(String email);

    /**
     * 설명 : 닉네임 유효성 체크와 중복 체크
     * @since : 2024.10.04
     * @author : T.S YUN
     */
    void checkNickname(String nickname);

    /**
     * 설명 : 사용자 닉네임 변경
     * @since : 2024.10.15
     * @author : T.S YUN
     */
    void changeNickname(String nickname, String memberEmail);

    /**
     * 설명 : 비밀번호 확인
     * @since : 2024.10.15
     * @author : T.S YUN
     */
    void checkOriginPassword(String enterPassword, String memberEmail);

    /**
     * 설명 : 비밀번호 변경
     * @since : 2024.10.15
     * @author : T.S YUN
     */
    void changePassword(String enterPassword, String memberEmail);

    /**
     * 설명 : 회원 가입 ( 일반 회원과 관리자를 구분하여 저장 )
     * @author : T.S YUN
     * @since : 2024.10.04
     */
    void registerMember(MemberRequestDto memberRequestDto);

    /**
     * 설명 : 댓글 member list
     * @author : T.S YUN
     * @since : 2024.10.04
     */
    Map<Integer, String> findUserNicknamesByMemberList(List<Integer> memberIdList);
}
