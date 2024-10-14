package com.example.reportservice.dto;

import com.example.reportservice.entity.Report;
import com.example.reportservice.entity.ReportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequestDto {
    @NotNull( message = "신고 당한 사람 입력이 없습니다")
    private int reporterMemberId;

    @NotNull( message = "신고한 사람 입력이 없습니다")
    private int reportedMemberId;

    @NotBlank( message = "신고 내용 입력이 없습니다")
    private String reportContent;

    @NotNull
    private ReportType reportType;

    @NotNull( message = "신고 타입의 ID가 없습니다")
    private int reportTypeId;

    public Report toEntity() {
        return Report.builder()
                .reporterMemberId(reporterMemberId)
                .reportedMemberId(reportedMemberId)
                .reportContent(reportContent)
                .reportType(reportType)
                .reportTypeId(reportTypeId)
                .build();
    }
}
