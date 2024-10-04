package com.example.adminservice.service;

import com.example.adminservice.dto.AdminRequestDto;
import com.example.adminservice.entity.Member;
import com.example.adminservice.mapper.AdminMapper;
import com.example.adminservice.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;


    @Override
    public void checkEmail(String email) {
        ValidationUtil.emailValidationCheck(email);

        boolean checkEmail = adminMapper.checkEmail(email);
        if (checkEmail) {
            throw new RuntimeException("email already in use");
        }
    }

    @Override
    public void checkNickname(String nickname) {
        ValidationUtil.nicknameValidationCheck(nickname);

        boolean checkNickName = adminMapper.checkNickName(nickname);
        if (checkNickName) {
            throw new RuntimeException("nickname already in use");
        }
    }

    @Override
    public void registerAdmin(AdminRequestDto adminRequestDto) {
        adminRequestDto.memberSignupValidator();

        Member member = adminRequestDto.toEntity();

        try {
            adminMapper.save(member);
        } catch (Exception e) {
            throw new RuntimeException("failed to save member");
        }
    }
}
