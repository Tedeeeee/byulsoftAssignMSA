package com.example.adminservice.service;

import com.example.adminservice.dto.AdminRequestDto;
import com.example.adminservice.dto.AdminResponseDto;
import com.example.adminservice.dto.MemberListResponseDto;
import com.example.adminservice.dto.MemberResponseDto;

public interface AdminService {

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
     * 설명 : 회원 가입 ( 일반 회원과 관리자를 구분하여 저장 )
     * @author : T.S YUN
     * @since : 2024.10.04
     */
    void registerAdmin(AdminRequestDto adminRequestDto);

    AdminResponseDto getAdmin(String adminEmail);

    /**
     * 설명 : 개인 사용자 정보 가져오기
     * @author : T.S YUN
     * @since : 2024.10.08
     */
    MemberResponseDto getMember(int memberId);

    /**
     * 설명 : 사용자 전체 데이터 가져오기
     *       전체 사용자인 만큼 모든 데이터를 가져올 필요는 없다.
     * @since : 2024.10.08
     * @author : T.S YUN
     */
    MemberListResponseDto getMemberList();

    MemberListResponseDto getMemberListByMemberNickname(String memberNickname);
}
