package com.example.adminreportservice.service;

import com.example.adminreportservice.dto.ReportRequestDto;
import com.example.adminreportservice.entity.Report;
import com.example.adminreportservice.mapper.ReportMapper;
import com.example.adminreportservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportMapper reportMapper;

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
}
