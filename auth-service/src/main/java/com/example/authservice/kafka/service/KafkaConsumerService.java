package com.example.authservice.kafka.service;

import com.example.authservice.dto.AuthMemberDto;
import com.example.authservice.entity.Member;
import com.example.authservice.mapper.MemberMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final MemberMapper memberMapper;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "register-topic")
    public void inputRegister(String kafkaMessage) {
        Map<String, Object> map = new HashMap<>();

        try {
            map = objectMapper.readValue(kafkaMessage, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }

        AuthMemberDto authMemberDto = AuthMemberDto.fromMap(map);

        Member member = authMemberDto.toEntity();

        memberMapper.save(member);
    }

    @KafkaListener(topics = "newPassword-topic")
    public void changePassword(String kafkaMessage) {
        Map<String, Object> map = new HashMap<>();

        try {
            map = objectMapper.readValue(kafkaMessage, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }

        AuthMemberDto authMemberDto = AuthMemberDto.fromMap(map);

        Member member = authMemberDto.toEntity();

        memberMapper.changePassword(member);
    }
}
