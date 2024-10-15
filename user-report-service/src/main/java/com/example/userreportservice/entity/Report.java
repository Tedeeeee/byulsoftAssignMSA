package com.example.userreportservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Report {
    private int reportId;
    private int reporterMemberId;
    private int reportedMemberId;
    private String reportContent;
    private ReportType reportType;
    private int reportTypeId;
    private ReportState reportState;
    private LocalDateTime reportCreatedAt;
    private LocalDateTime reportUpdatedAt;

    public void checkReportCancelableByReporter (int memberId) {
        if (reporterMemberId != memberId) {
            throw new RuntimeException("신고자가 일치하지 않습니다");
        }
    }

    public void checkReportProcessed() {
        if (reportState == ReportState.COMPLETED || reportState == ReportState.REVOKE) {
            throw new RuntimeException("이미 처리 된 신고입니다");
        }
    }
}
