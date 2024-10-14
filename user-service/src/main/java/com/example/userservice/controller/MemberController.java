package com.example.userservice.controller;

import com.example.userservice.commonApi.BodyResponse;
import com.example.userservice.dto.MemberRequestDto;
import com.example.userservice.dto.MemberResponseDto;
import com.example.userservice.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping
    public ResponseEntity<BodyResponse<MemberResponseDto>> getUserInfo(HttpServletRequest request) {
        String email = request.getHeader("memberEmail");
        MemberResponseDto memberResponse = memberService.findUserByMemberEmail(email);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(memberResponse));
    }
}
