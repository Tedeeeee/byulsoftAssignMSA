package com.example.adminservice.service;

import com.example.adminservice.dto.AdminRequestDto;

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
}
