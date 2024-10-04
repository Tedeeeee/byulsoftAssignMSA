package com.example.adminservice.mapper;

import com.example.adminservice.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    int save(Member member);
    boolean checkNickName(String memberNickName);
    boolean checkEmail(String memberEmail);
}
