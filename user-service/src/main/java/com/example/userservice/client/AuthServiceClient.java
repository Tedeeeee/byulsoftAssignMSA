package com.example.userservice.client;

import com.example.userservice.dto.AuthMemberDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "authServiceClient", url = "http://auth-service:8081")
public interface AuthServiceClient {
    /**
     * 설명 : 사용자 데이터 저장
     * @author : T.S YUN
     * @since : 2024.10.21
     */
    @PostMapping("/auth")
    void memberDataSave(@RequestBody AuthMemberDto authMemberDto);

    /**
     * 설명 : 비밀번호 변경
     * @since : 2024.10.21
     * @author : T.S YUN
     */
    @PostMapping("/auth/change-password")
    void changePassword(@RequestBody AuthMemberDto authMemberDto);
}
