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

    @PostMapping("/changeNickname")
    public ResponseEntity<BodyResponse<String>> changeNickname(@RequestBody MemberRequestDto memberRequestDto, HttpServletRequest request) {
        String memberEmail = request.getHeader("memberEmail");
        memberService.changeNickname(memberRequestDto.getMemberNickname(), memberEmail);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("닉네임 수정이 성공하였습니다"));
    }

    @PostMapping("/checkOriginPassword")
    public ResponseEntity<BodyResponse<String>> checkMyPassword(@RequestBody MemberRequestDto memberRequestDto, HttpServletRequest request) {
        String memberEmail = request.getHeader("memberEmail");
        memberService.checkOriginPassword(memberRequestDto.getMemberPassword(), memberEmail);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("비밀번호 확인이 성공하였습니다"));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<BodyResponse<String>> changePassword(@RequestBody MemberRequestDto memberRequestDto, HttpServletRequest request) {
        String memberEmail = request.getHeader("memberEmail");
        memberService.changePassword(memberRequestDto.getMemberPassword(), memberEmail);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("비밀번호 변경이 성공하였습니다 다시 로그인 해주세요"));
    }
}
