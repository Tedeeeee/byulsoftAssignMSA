package com.example.userboardservice.client;

import com.example.userboardservice.dto.MemberResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "userServiceClient", url = "http://user-service:8082")
public interface MemberServiceClient {
    /**
     * 설명 : user service 로부터 사용자 정보 가져오기
     * @since : 2024.10.07
     * @author : T.S YUN
     */
    @GetMapping("/noAuth/users/feign")
    MemberResponseDto getMemberByMemberEmail(@RequestParam String memberEmail);

    /**
     * 설명 : 게시글 혹은 댓글의 작성자 닉네임을 가져오기
     * @since : 2024.10.07
     * @author : T.S YUN
     */
    @GetMapping("/noAuth/users/feign/{memberId}/nickname")
    String getMemberNicknameByMemberId(@PathVariable int memberId);

    /**
     * 설명 : 멤버 ID 리스트에 대한 닉네임 목록 가져오기
     * @since : 2024.10.13
     * @author : T.S YUN
     */
    @GetMapping("/noAuth/users/feign/nicknames")
    Map<Integer, String> getMemberNicknamesByMemberIdList(@RequestParam List<Integer> memberIdList);
}
