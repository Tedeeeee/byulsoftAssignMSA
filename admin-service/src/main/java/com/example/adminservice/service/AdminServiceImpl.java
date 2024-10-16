package com.example.adminservice.service;

import com.example.adminservice.client.MemberServiceClient;
import com.example.adminservice.dto.*;
import com.example.adminservice.entity.AdminMember;
import com.example.adminservice.kafka.service.KafkaProducerSendService;
import com.example.adminservice.mapper.AdminMapper;
import com.example.adminservice.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;
    private final MemberServiceClient memberServiceClient;
    private final KafkaProducerSendService kafkaProducerSendService;

    @Override
    public void checkEmail(String email) {
        ValidationUtil.emailValidationCheck(email);

        boolean checkEmail = adminMapper.checkEmail(email);
        if (checkEmail) {
            throw new RuntimeException("이미 사용중인 이메일 입니다");
        }
    }

    @Override
    public void checkNickname(String nickname) {
        ValidationUtil.nicknameValidationCheck(nickname);

        boolean checkNickName = adminMapper.checkNickName(nickname);
        if (checkNickName) {
            throw new RuntimeException("이미 사용중인 닉네임 입니다");
        }
    }

    @Override
    public void registerAdmin(AdminRequestDto adminRequestDto) {
        checkEmail(adminRequestDto.getAdminEmail());
        checkNickname(adminRequestDto.getAdminNickname());

        AdminMember adminMember = adminRequestDto.toEntity();

        try {
            adminMapper.save(adminMember);
            AuthMemberDto authMember = AuthMemberDto.from(adminMember);
            // 만약 kafka의 서버가 장애가 발생해서 처리가 안되었다면?
            // 해당 회원가입도 되지 않아야 한다.
            kafkaProducerSendService.send("register-topic", authMember);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("무결성 문제");
        } catch (Exception e) {
            throw new RuntimeException("저장 실패" + e.getMessage());
        }
    }

    @Override
    public AdminResponseDto getAdmin(String adminEmail) {
        AdminMember admin = adminMapper.getAdminByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 관리자 입니다"));

        return AdminResponseDto.from(admin);
    }

    @Override
    public MemberResponseDto getMember(int memberId) {
        // Feign 사용해서 사용자 정보 가져오기
        return memberServiceClient.getMemberByMemberId(memberId);
    }

    // 검색이 추가로 발생 할 수 있다
    @Override
    public MemberListResponseDto getMemberList() {
        // Feign 사용해서 사용자 정보"들"
        List<MemberResponseDto> memberAll = memberServiceClient.getMemberAll();

        return MemberListResponseDto.from(memberAll, memberAll.size());
    }
}
