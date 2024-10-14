package com.example.userreportservice.service;

import com.example.userreportservice.dto.ReportRequestDto;
import com.example.userreportservice.entity.Report;
import com.example.userreportservice.mapper.ReportMapper;
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
    public void revokeReport(int reportId) {

    }
}
