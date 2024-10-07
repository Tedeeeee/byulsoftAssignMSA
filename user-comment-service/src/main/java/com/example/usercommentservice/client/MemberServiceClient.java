package com.example.usercommentservice.client;

import com.example.usercommentservice.dto.MemberResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userServiceClient", url = "http://localhost:8888")
public interface MemberServiceClient {
    /**
     * 설명 : user service 로부터 사용자 정보 가져오기
     * @since : 2024.10.07
     * @author : T.S YUN
     */
    @GetMapping("/api/userService/feign")
    MemberResponseDto getMemberByMemberEmail(@RequestParam String memberEmail);

    /**
     * 설명 : 게시글 혹은 댓글의 작성자 닉네임을 가져오기
     * @since : 2024.10.07
     * @author : T.S YUN
     */
    @GetMapping("/api/userService/feign/{memberId}/nickname")
    String getMemberNicknameByMemberId(@PathVariable int memberId);
}

