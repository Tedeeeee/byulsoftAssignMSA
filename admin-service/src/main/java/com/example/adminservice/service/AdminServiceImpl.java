package com.example.adminservice.service;

import com.example.adminservice.dto.AdminRequestDto;
import com.example.adminservice.dto.AdminListResponseDto;
import com.example.adminservice.dto.AdminResponseDto;
import com.example.adminservice.dto.AuthMemberDto;
import com.example.adminservice.entity.AdminMember;
import com.example.adminservice.kafka.service.KafkaProducerSendService;
import com.example.adminservice.mapper.AdminMapper;
import com.example.adminservice.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;
    private final KafkaProducerSendService kafkaProducerSendService;


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
            AuthMemberDto authMember = AuthMemberDto.from(adminMember);
            // 만약 kafka의 서버가 장애가 발생해서 처리가 안되었다면?
            // 해당 회원가입도 되지 않아야 한다.
            kafkaProducerSendService.send("register-topic", authMember);
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
