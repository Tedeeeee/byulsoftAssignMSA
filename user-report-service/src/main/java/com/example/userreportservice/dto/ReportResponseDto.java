package com.example.userreportservice.dto;

import com.example.userreportservice.entity.Report;
import com.example.userreportservice.entity.ReportState;
import com.example.userreportservice.util.TimeChangerUtil;
import jakarta.validation.constraints.NotNull;
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
    private ReportState reportState;
    private String reportedMemberNickname;
    private String reportContent;
    private String reportDate;

    public static ReportResponseDto from(Report report, String memberNickname) {
        return ReportResponseDto.builder()
                .reportId(report.getReportId())
                .reportState(report.getReportState())
                .reportedMemberNickname(memberNickname)
                .reportContent(report.getReportContent())
                .reportDate(TimeChangerUtil.timeChange(report.getReportCreatedAt()))
                .build();
    }
}
