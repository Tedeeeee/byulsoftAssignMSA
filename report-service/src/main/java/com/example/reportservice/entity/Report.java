package com.example.reportservice.entity;

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
}
