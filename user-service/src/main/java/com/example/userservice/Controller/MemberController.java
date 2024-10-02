package com.example.userservice.Controller;

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


    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    // API gateway를 통한 환경은 해당 메소드를 사용
    @GetMapping()
    public ResponseEntity<MemberResponseDto> getUserInfo(HttpServletRequest request) {
        String email = request.getHeader("email");
        MemberResponseDto memberResponse = memberService.findUserByMemberEmail(email);
        return new ResponseEntity<>(memberResponse, HttpStatus.OK);
    }



}
