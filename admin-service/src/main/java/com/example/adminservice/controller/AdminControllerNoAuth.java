package com.example.adminservice.controller;

import com.example.adminservice.commonApi.BodyResponse;
import com.example.adminservice.dto.AdminRequestDto;
import com.example.adminservice.dto.AdminResponseDto;
import com.example.adminservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/noAuth/admins")
@RequiredArgsConstructor
public class AdminControllerNoAuth {

    private final AdminService adminService;

    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/check-email")
    public ResponseEntity<BodyResponse<String>> checkEmail(@RequestParam String email) {
        adminService.checkEmail(email);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("이메일 사용이 가능합니다"));
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<BodyResponse<String>> checkNickname(@RequestParam String nickname) {
        adminService.checkNickname(nickname);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("닉네임 사용이 가능합니다"));
    }

    @PostMapping("/register")
    public ResponseEntity<BodyResponse<String>> registerAdmin(@RequestBody AdminRequestDto adminRequestDto) {
        adminService.registerAdmin(adminRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BodyResponse.success("회원가입이 완료되었습니다"));
    }

    // feign 요청
    @GetMapping("/feign/nicknames")
    Map<Integer, String> getAdminNicknamesByAdminIdList(@RequestParam List<Integer> adminIdList) {
        return adminService.findAdminNicknamesByAdminList(adminIdList);
    }

    @GetMapping("/feign/info")
    AdminResponseDto getAdminByAdminEmail(@RequestParam String adminEmail) {
        return adminService.getAdmin(adminEmail);
    };
}