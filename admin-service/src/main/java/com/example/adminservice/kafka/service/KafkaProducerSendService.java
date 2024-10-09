package com.example.adminservice.kafka.service;

import com.example.adminservice.dto.AuthMemberDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerSendService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void send(String topic, AuthMemberDto authMemberDto) {
        String jsonInString = "";

        try {
            jsonInString = objectMapper.writeValueAsString(authMemberDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonInString);
    }
}
