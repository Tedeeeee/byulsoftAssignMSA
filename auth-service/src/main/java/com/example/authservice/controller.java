package com.example.authservice;

import com.example.authservice.entity.Member;
import com.example.authservice.entity.Role;
import com.example.authservice.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class controller {

    private final MemberMapper memberMapper;

    @GetMapping()
    public ResponseEntity<String> hello() {
        System.out.println("hello");
        return new ResponseEntity<>("성공", HttpStatus.OK);
    }

    @GetMapping("/insert")
    public ResponseEntity<String> insert() {
        Member member = Member.builder()
                .memberEmail("test222@test.com")
                .memberPassword("123123123")
                .memberNickname("test")
                .memberName("test")
                .memberRole(Role.ADMIN)
                .build();

        memberMapper.save(member);
        return new ResponseEntity<>("성공", HttpStatus.OK);
    }
}
