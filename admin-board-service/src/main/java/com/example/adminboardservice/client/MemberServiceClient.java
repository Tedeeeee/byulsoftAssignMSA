package com.example.adminboardservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userServiceClient", url = "http://localhost:8888")
public interface MemberServiceClient {
    /**
     * 설명 : 게시글 혹은 댓글의 작성자 닉네임을 가져오기
     * @author : T.S YUN
     * @since : 2024.10.07
     */
    @GetMapping("/api/userService/noAuth/users/feign/{memberId}/nickname")
    String getMemberNicknameByMemberId(@PathVariable int memberId);



}