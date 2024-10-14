package com.example.reportservice.mapper;

import com.example.reportservice.entity.Report;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportMapper {
    void save(Report report);
    void completeReport(int reportId);
    void revokeReport(int reportId);
}
