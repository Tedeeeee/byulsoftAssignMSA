package com.example.reportservice.service;

import com.example.reportservice.dto.ReportRequestDto;
import com.example.reportservice.entity.Report;
import com.example.reportservice.mapper.ReportMapper;
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
