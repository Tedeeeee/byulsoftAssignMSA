package com.example.userreportservice.mapper;

import com.example.userreportservice.entity.Report;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportMapper {
    void save(Report report);
    void revokeReport(int reportId);
}
