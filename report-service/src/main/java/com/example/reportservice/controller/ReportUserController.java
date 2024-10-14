package com.example.reportservice.controller;

import com.example.reportservice.commonApi.BodyResponse;
import com.example.reportservice.dto.ReportRequestDto;
import com.example.reportservice.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/reports")
@RequiredArgsConstructor
public class ReportUserController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<BodyResponse<String>> createReport(@RequestBody @Valid ReportRequestDto reportRequestDto) {
        reportService.createReport(reportRequestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("신고가 접수되었습니다"));
    }

}
