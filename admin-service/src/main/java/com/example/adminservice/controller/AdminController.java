package com.example.adminservice.controller;

import com.example.adminservice.commonApi.BodyResponse;
import com.example.adminservice.dto.AdminRequestDto;
import com.example.adminservice.dto.AdminListResponseDto;
import com.example.adminservice.dto.AdminResponseDto;
import com.example.adminservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // 개인 사용자
    @GetMapping("/users/{memberId}")
    public ResponseEntity<BodyResponse<AdminResponseDto>> getMemberProfile(@PathVariable int memberId) {
        AdminResponseDto member = adminService.getMember(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(member));
    }

    // 전체 사용자....
    @GetMapping("/users")
    public ResponseEntity<BodyResponse<AdminListResponseDto>> getUsers() {
        AdminListResponseDto memberList = adminService.getMemberList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(memberList));
    }

}
