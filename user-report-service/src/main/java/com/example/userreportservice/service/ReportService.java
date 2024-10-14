package com.example.userreportservice.service;

import com.example.userreportservice.dto.ReportRequestDto;

public interface ReportService {
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
    void revokeReport(int reportId);

}
