package com.example.userreportservice.client;

import com.example.userreportservice.dto.MemberResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "userServiceClient", url = "http://localhost:8888")
public interface MemberServiceClient {
    /**
     * 설명 : MemberNickname 으로 사용자 Id가져오기
     * @since : 2024.10.15
     * @author : T.S YUN
     */
    @GetMapping("/api/userService/noAuth/users/feign/nickname/{memberNickname}")
    MemberResponseDto getMemberIdByMemberNickname(@PathVariable String memberNickname);

    /**
     * 설명 : user service 로부터 사용자 정보 가져오기
     * @since : 2024.10.15
     * @author : T.S YUN
     */
    @GetMapping("/api/userService/noAuth/users/feign")
    MemberResponseDto getMemberByMemberEmail(@RequestParam String memberEmail);

    /**
     * 설명 : 멤버 ID 리스트에 대한 닉네임 목록 가져오기
     * @since : 2024.10.15
     * @author : T.S YUN
     */
    @GetMapping("/api/userService/noAuth/users/feign/nicknames")
    Map<Integer, String> getMemberNicknamesByMemberIdList(@RequestParam List<Integer> memberIdList);
}

