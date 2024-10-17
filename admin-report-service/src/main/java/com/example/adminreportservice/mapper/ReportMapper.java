package com.example.adminreportservice.mapper;

import com.example.adminreportservice.entity.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {
    void save(Report report);
    void completeReport(int reportId);
    void revokeReport(int reportId);
    Integer totalCountByMemberId(int memberId);
    List<Report> getReportsByMemberId(int memberId);
}
