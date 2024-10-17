package com.example.userreportservice.service;

import com.example.userreportservice.client.MemberServiceClient;
import com.example.userreportservice.dto.MemberResponseDto;
import com.example.userreportservice.dto.ReportRequestDto;
import com.example.userreportservice.dto.ReportResponseDto;
import com.example.userreportservice.entity.Report;
import com.example.userreportservice.mapper.ReportMapper;
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
    public List<ReportResponseDto> getMyReports(String memberEmail) {
        MemberResponseDto member = memberServiceClient.getMemberByMemberEmail(memberEmail);

        List<Report> myReport = reportMapper.getMyReport(member.getMemberId());

        List<Integer> memberIdList = myReport.stream().map(Report::getReportedMemberId).toList();

        Map<Integer, String> memberNicknameList = memberServiceClient.getMemberNicknamesByMemberIdList(memberIdList);

        return myReport.stream()
                .map(report -> ReportResponseDto.from(report, memberNicknameList.get(report.getReportedMemberId())))
                .toList();
    }

    @Override
    public void createReport(ReportRequestDto reportRequestDto) {
        MemberResponseDto member = memberServiceClient.getMemberIdByMemberNickname(reportRequestDto.getReportedMemberNickname());
        Report report = reportRequestDto.toEntity(member.getMemberId());

        if (reportMapper.isReportExist(report)) {
            throw new RuntimeException("중복 신고는 불가능 합니다");
        }

        reportMapper.save(report);
    }

    @Override
    public void revokeReport(int reportId, String memberEmail) {
        MemberResponseDto member = memberServiceClient.getMemberByMemberEmail(memberEmail);
        Report report = reportMapper.getReportById(reportId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 신고입니다"));

        report.checkReportCancelableByReporter(member.getMemberId());
        report.checkReportProcessed();

        reportMapper.revokeReport(reportId);
    }
}
