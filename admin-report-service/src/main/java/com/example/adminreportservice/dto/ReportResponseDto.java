package com.example.adminreportservice.dto;

import com.example.adminreportservice.entity.Report;
import com.example.adminreportservice.entity.ReportState;
import com.example.adminreportservice.entity.ReportType;
import com.example.adminreportservice.util.TimeChangerUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportResponseDto {

    private int reportId;
    private String reportedMemberNickname;
    private String reportContent;
    private ReportType reportType;
    private int reportTypeId;
    private String reportCreatedAt;
    private ReportState reportState;

    public static ReportResponseDto from(Report report, String memberNickname) {
        return ReportResponseDto.builder()
                .reportId(report.getReportId())
                .reportedMemberNickname(memberNickname)
                .reportContent(report.getReportContent())
                .reportType(report.getReportType())
                .reportTypeId(report.getReportTypeId())
                .reportCreatedAt(TimeChangerUtil.timeChange(report.getReportCreatedAt()))
                .reportState(report.getReportState())
                .build();
    }
}
