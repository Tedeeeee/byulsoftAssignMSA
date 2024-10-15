package com.example.userreportservice.mapper;

import com.example.userreportservice.entity.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ReportMapper {
    void save(Report report);
    List<Report> getMyReport(int memberId);
    boolean isReportExist(Report report);
    void revokeReport(int reportId);
    Optional<Report> getReportById(int reportId);
}
