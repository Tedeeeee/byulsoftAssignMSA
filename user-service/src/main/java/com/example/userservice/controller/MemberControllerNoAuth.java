package com.example.userservice.controller;

import com.example.userservice.commonApi.BodyResponse;
import com.example.userservice.dto.MemberRequestDto;
import com.example.userservice.dto.MemberResponseDto;
import com.example.userservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/noAuth/users")
public class MemberControllerNoAuth {

    private final MemberService memberService;

    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
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
                .body(BodyResponse.success("회원가입이 완료되었습니다"));
    }


    // feign 요청 전용
    @GetMapping("/feign")
    MemberResponseDto getMembers(@RequestParam String memberEmail) {
        return memberService.findUserByMemberEmail(memberEmail);
    }

    @GetMapping("/feign/{memberId}/nickname")
    String getMemberNickname(@PathVariable("memberId") int memberId) {
        return memberService.findUserNicknameByMemberId(memberId);
    }

    @GetMapping("/feign/nicknames")
    Map<Integer, String> getMemberNicknamesByMemberIdList(@RequestParam List<Integer> memberIdList) {
        return memberService.findUserNicknamesByMemberList(memberIdList);
    }

    @GetMapping("/feign/nickname/{memberNickname}")
    MemberResponseDto getMemberNickname(@PathVariable("memberNickname") String memberNickname) {
        return memberService.findMemberIdByMemberNickname(memberNickname);
    }

    @GetMapping("/feign/members/nickname")
    List<MemberResponseDto> getMembersByNickname(@RequestParam("memberNickname") String memberNickname) {
        return memberService.findMembersByMemberEmail(memberNickname);
    }

    @GetMapping("/feign/all")
    List<MemberResponseDto> getAllMembers() {
        return memberService.findMemberAll();
    }

    @GetMapping("/feign/{memberId}")
    MemberResponseDto getMemberById(@PathVariable("memberId") int memberId) {
        return memberService.findMemberByMemberId(memberId);
    }
}
