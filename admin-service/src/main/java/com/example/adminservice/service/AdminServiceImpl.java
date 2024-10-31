package com.example.adminservice.service;

import com.example.adminservice.client.AuthServiceClient;
import com.example.adminservice.client.MemberServiceClient;
import com.example.adminservice.dto.*;
import com.example.adminservice.entity.AdminMember;
import com.example.adminservice.mapper.AdminMapper;
import com.example.adminservice.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;
    private final MemberServiceClient memberServiceClient;
    private final AuthServiceClient authServiceClient;

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

        adminMapper.save(adminMember);
        AuthMemberDto authMember = AuthMemberDto.from(adminMember);

        authServiceClient.adminDataSave(authMember);
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

    @Override
    public MemberListResponseDto getMemberListByMemberNickname(String memberNickname) {
        List<MemberResponseDto> memberList = memberServiceClient.getMemberByNickname(memberNickname);

        return MemberListResponseDto.from(memberList, memberList.size());
    }

    @Override
    public Map<Integer, String> findAdminNicknamesByAdminList(List<Integer> adminIdList) {
        // 여기서 list 순서대로 가져와야 한다.
        List<AdminMember> findMemberList = adminMapper.findAdminNicknameByAdminIdList(adminIdList);

        return findMemberList.stream()
                .collect(Collectors.toMap(AdminMember::getAdminId,
                        admin -> Optional.ofNullable(admin.getAdminNickname()).orElse("탈퇴 회원")));
    }
}
