package com.example.userservice;

import com.example.userservice.mapper.MemberMapper;
import com.example.userservice.service.MemberService;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class testCallStack {

    private static final Logger log = LoggerFactory.getLogger(testCallStack.class);
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemberService memberService;

    @Transactional
    public void callingTransactionMethod() {
        try {
            memberService.registerMember();
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
        }
    }


}
