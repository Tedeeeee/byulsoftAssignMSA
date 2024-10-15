package com.example.userreportservice.service;

import com.example.userreportservice.dto.ReportRequestDto;
import com.example.userreportservice.dto.ReportResponseDto;

import java.util.List;

public interface ReportService {

    /**
     * 설명 : 나의 신고 내역 가져오기
     * @author : T.S YUN
     * @since : 2024.10.15
     */
    List<ReportResponseDto> getMyReports(String memberEmail);

    /**
     * 설명 : 신고 하기
     * @since : 2024.10.14
     * @author : T.S YUN
     */
    void createReport(ReportRequestDto reportRequestDto);

    /**
     * 설명 : 신고 취소
     * @since : 2024.10.14
     * @author : T.S YUN
     */
    void revokeReport(int reportId, String memberEmail);

}
