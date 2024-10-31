package com.example.adminservice.client;

import com.example.adminservice.dto.AuthMemberDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "authServiceClient", url = "http://auth-service:8081")
public interface AuthServiceClient {
    /**
     * 설명 : 관리자 데이터 저장
     * @author : T.S YUN
     * @since : 2024.10.21
     */
    @PostMapping("/auth")
    void adminDataSave(@RequestBody AuthMemberDto authMemberDto);
}
