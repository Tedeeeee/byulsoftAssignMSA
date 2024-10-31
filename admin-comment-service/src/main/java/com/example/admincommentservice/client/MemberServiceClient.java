package com.example.admincommentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "userServiceClient", url = "http://user-service:8082")
public interface MemberServiceClient {
    /**
     * 설명 : 멤버 ID 리스트에 대한 닉네임 목록 가져오기
     * @since : 2024.10.13
     * @author : T.S YUN
     */
    @GetMapping("/noAuth/users/feign/nicknames")
    Map<Integer, String> getMemberNicknamesByMemberIdList(@RequestParam List<Integer> memberIdList);
}

