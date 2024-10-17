package com.example.adminservice.controller;

import com.example.adminservice.commonApi.BodyResponse;
import com.example.adminservice.dto.AdminResponseDto;
import com.example.adminservice.dto.MemberListResponseDto;
import com.example.adminservice.dto.MemberResponseDto;
import com.example.adminservice.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<BodyResponse<AdminResponseDto>> getAdmins(HttpServletRequest request) {
        String adminEmail = request.getHeader("adminEmail");
        AdminResponseDto admin = adminService.getAdmin(adminEmail);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(admin));
    }

    // 개인 사용자 - 관리자 X
    @GetMapping("/users/{memberId}")
    public ResponseEntity<BodyResponse<MemberResponseDto>> getMemberProfile(@PathVariable int memberId) {
        MemberResponseDto member = adminService.getMember(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(member));
    }

    // 전체 사용자....
    @GetMapping("/users")
    public ResponseEntity<BodyResponse<MemberListResponseDto>> getUsers() {
        MemberListResponseDto memberList = adminService.getMemberList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(memberList));
    }

    @GetMapping("/users/nickname")
    public ResponseEntity<BodyResponse<MemberListResponseDto>> getMemberNickname(@RequestParam String memberNickname) {
        MemberListResponseDto memberList = adminService.getMemberListByMemberNickname(memberNickname);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(memberList));
    }
}
