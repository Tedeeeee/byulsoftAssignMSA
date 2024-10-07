package com.example.userservice.controller;

import com.example.userservice.dto.MemberResponseDto;
import com.example.userservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feign")
public class FeignController {

    private final MemberService memberService;

    @GetMapping()
    MemberResponseDto getMembers(@RequestParam String memberEmail) {
        return memberService.findUserByMemberEmail(memberEmail);
    }

    @GetMapping("{memberId}/nickname")
    String getMemberNickname(@PathVariable("memberId") int memberId) {
        return memberService.findUserNicknameByMemberId(memberId);
    }
}
