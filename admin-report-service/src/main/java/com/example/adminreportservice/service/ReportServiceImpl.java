package com.example.adminreportservice.service;

import com.example.adminreportservice.client.MemberServiceClient;
import com.example.adminreportservice.dto.ReportRequestDto;
import com.example.adminreportservice.dto.ReportResponseDto;
import com.example.adminreportservice.entity.Report;
import com.example.adminreportservice.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportMapper reportMapper;
    private final MemberServiceClient memberServiceClient;

    @Override
    public void createReport(ReportRequestDto reportRequestDto) {
        Report report = reportRequestDto.toEntity();

        reportMapper.save(report);
    }

    @Override
    public void completeReport(int reportId) {

    }

    @Override
    public void revokeReport(int reportId) {

    }

    @Override
    public int getTotalCountReport(int memberId) {
        return reportMapper.totalCountByMemberId(memberId);
    }

    @Override
    public List<ReportResponseDto> getReportsByMemberId(int memberId) {
        List<Report> reportList = reportMapper.getReportsByMemberId(memberId);

        List<Integer> list = reportList.stream()
                .map(Report::getReportedMemberId).toList();

        Map<Integer, String> memberNicknameMap = memberServiceClient.getMemberNicknamesByMemberIdList(list);

        return  reportList.stream()
                .map(report ->
                        ReportResponseDto.from(report, memberNicknameMap.get(report.getReportedMemberId()))
                ).toList();
    }
}
