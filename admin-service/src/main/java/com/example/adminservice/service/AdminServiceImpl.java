package com.example.adminservice.service;

import com.example.adminservice.dto.AdminRequestDto;
import com.example.adminservice.dto.AdminListResponseDto;
import com.example.adminservice.dto.AdminResponseDto;
import com.example.adminservice.entity.AdminMember;
import com.example.adminservice.mapper.AdminMapper;
import com.example.adminservice.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

        AdminMember adminMember = adminRequestDto.toEntity();

        try {
            adminMapper.save(adminMember);
        } catch (Exception e) {
            throw new RuntimeException("failed to save member");
        }
    }

    @Override
    public AdminResponseDto getMember(int memberId) {
        // Feign 사용해서 사용자 정보 가져오기

        return null;
    }

    // 검색이 추가로 발생 할 수 있다
    @Override
    public AdminListResponseDto getMemberList() {
        // Feign 사용해서 사용자 정보"들"
        // total 인원 수

        return null;
    }
}
