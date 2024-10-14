package com.example.userreportservice.controller;

import com.example.userreportservice.commonApi.BodyResponse;
import com.example.userreportservice.dto.ReportRequestDto;
import com.example.userreportservice.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportUserController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<BodyResponse<String>> createReport(@RequestBody @Valid ReportRequestDto reportRequestDto) {
        reportService.createReport(reportRequestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("신고가 접수되었습니다"));
    }

    @PostMapping("/revoke/{reportId}")
    public ResponseEntity<BodyResponse<String>> revokeReport(@PathVariable int reportId) {
        reportService.revokeReport(reportId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("신고가 취소되었습니다"));
    }
}
