package com.example.adminservice.mapper;

import com.example.adminservice.entity.AdminMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AdminMapper {
    int save(AdminMember adminMember);
    boolean checkNickName(String adminNickName);
    boolean checkEmail(String adminEmail);
    List<AdminMember> getMembers();
    Optional<AdminMember> getMemberByMemberId(int memberId);
    int totalCount();
}
