package com.example.userservice.service;

import com.example.userservice.dto.MemberResponseDto;

public interface MemberService {

    /**
     * 설명 : memberId를 통해 사용자의 정보를 가져온다.
     * @since : 2024.10.02
     * @author : T.S YUN
     * @return MemberResponseDto
     */
    MemberResponseDto findUserByMemberEmail(String memberEmail);
}
