package com.example.reportservice.controller;

import com.example.reportservice.commonApi.BodyResponse;
import com.example.reportservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
public class ReportAdminController {

    private final ReportService reportService;

    @PostMapping("/changeStatus/{reportId}")
    public ResponseEntity<BodyResponse<String>> changeStatus(@PathVariable int reportId) {
        reportService.completeReport(reportId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("신고가 처리되었습니다"));
    }

    @PostMapping("/revokeReport/{reportId}")
    public ResponseEntity<BodyResponse<String>> revokeReport(@PathVariable int reportId) {
        reportService.revokeReport(reportId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("신고가 취소되었습니다"));
    }
}
