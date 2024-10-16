package com.example.adminservice.client;

import com.example.adminservice.dto.MemberResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "userServiceClient", url = "http://localhost:8888")
public interface MemberServiceClient {
    /**
     * 설명 : user service 로부터 서비스 사용자 전체 가져오기
     * @author : T.S YUN
     * @since : 2024.10.16
     */
    @GetMapping("/api/userService/noAuth/users/feign/all")
    List<MemberResponseDto> getMemberAll();

    /**
     * 설명 : 특정 ID로 사용자 정보 가져오기
     * @author : T.S YUN
     * @since : 2024.10.16
     */
    @GetMapping("/api/userService/noAuth/users/feign/{memberId}")
    MemberResponseDto getMemberByMemberId(@PathVariable int memberId);
}
