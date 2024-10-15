package com.example.userreportservice.controller;

import com.example.userreportservice.commonApi.BodyResponse;
import com.example.userreportservice.dto.ReportRequestDto;
import com.example.userreportservice.dto.ReportResponseDto;
import com.example.userreportservice.service.ReportService;
import com.example.userreportservice.service.ReportServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final ReportServiceImpl reportServiceImpl;

    @GetMapping
    public ResponseEntity<BodyResponse<List<ReportResponseDto>>> getMyReports(HttpServletRequest request) {
        String memberEmail = request.getHeader("memberEmail");
        List<ReportResponseDto> myReports = reportService.getMyReports(memberEmail);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success(myReports));
    }

    @PostMapping
    public ResponseEntity<BodyResponse<String>> createReport(@RequestBody @Valid ReportRequestDto reportRequestDto) {
        reportService.createReport(reportRequestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("신고가 접수되었습니다"));
    }

    @PostMapping("/revoke/{reportId}")
    public ResponseEntity<BodyResponse<String>> revokeReport(@PathVariable int reportId,
                                                             HttpServletRequest request) {
        String memberEmail = request.getHeader("memberEmail");
        reportService.revokeReport(reportId, memberEmail);

        return ResponseEntity.status(HttpStatus.OK)
                .body(BodyResponse.success("신고가 취소되었습니다"));
    }
}
