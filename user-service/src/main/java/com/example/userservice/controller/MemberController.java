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

    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    // API gateway를 통한 환경은 해당 메소드를 사용
    @GetMapping
    public ResponseEntity<BodyResponse<MemberResponseDto>> getUserInfo(HttpServletRequest request) {
        String email = request.getHeader("memberEmail");
        MemberResponseDto memberResponse = memberService.findUserByMemberEmail(email);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(memberResponse));
    }

    @GetMapping("/check-email")
    public ResponseEntity<BodyResponse<String>> checkEmail(@RequestParam String email) {
        memberService.checkEmail(email);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("이메일 사용이 가능합니다"));
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<BodyResponse<String>> checkNickname(@RequestParam String nickname) {
        memberService.checkNickname(nickname);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("닉네임 사용이 가능합니다"));
    }

    @PostMapping("register")
    public ResponseEntity<BodyResponse<String>> registerMember(@RequestBody MemberRequestDto memberRequestDto) {
        memberService.registerMember(memberRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BodyResponse.createSuccess("회원가입이 완료되었습니다"));
    }
}
