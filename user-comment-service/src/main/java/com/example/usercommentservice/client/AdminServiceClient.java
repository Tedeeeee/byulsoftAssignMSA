package com.example.usercommentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "adminServiceClient", url = "http://localhost:8888")
public interface AdminServiceClient {

    /**
     * 설명 : 관리자 ID 리스트에 대한 닉네임 목록 가져오기
     * @since : 2024.10.18
     * @author : T.S YUN
     */
    @GetMapping("/api/adminService/noAuth/admins/feign/nicknames")
    Map<Integer, String> getAdminNicknamesByAdminIdList(@RequestParam List<Integer> adminIdList);
}
