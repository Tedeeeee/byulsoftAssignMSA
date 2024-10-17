package com.example.adminreportservice.controller;

import com.example.adminreportservice.commonApi.BodyResponse;
import com.example.adminreportservice.dto.ReportRequestDto;
import com.example.adminreportservice.dto.ReportResponseDto;
import com.example.adminreportservice.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/count/{memberId}")
    public ResponseEntity<BodyResponse<Integer>> countMember(@PathVariable int memberId) {
        int totalCountReport = reportService.getTotalCountReport(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(totalCountReport));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<BodyResponse<List<ReportResponseDto>>> findAllReports(@PathVariable int memberId) {
        List<ReportResponseDto> reports = reportService.getReportsByMemberId(memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(reports));
    }
}
