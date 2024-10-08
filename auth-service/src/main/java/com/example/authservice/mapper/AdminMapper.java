package com.example.authservice.mapper;

import com.example.authservice.entity.AdminMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface AdminMapper {
    void saveRefreshToken(@Param("adminId") int adminId, @Param("adminRefreshToken") String refreshToken);
    AdminMember findAdminByAdminEmail(String adminEmail);
    Optional<AdminMember> findAdminByRefreshToken(String refreshToken);
}
