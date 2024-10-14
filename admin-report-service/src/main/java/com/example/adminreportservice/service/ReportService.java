package com.example.adminreportservice.service;

import com.example.adminreportservice.dto.ReportRequestDto;

public interface ReportService {
    /**
     * 설명 : 신고 하기
     * @since : 2024.10.14
     * @author : T.S YUN
     */
    void createReport(ReportRequestDto reportRequestDto);

    /**
     * 설명 : 신고 상태 변경
     * @since : 2024.10.14
     * @author : T.S YUN
     */
    void completeReport(int reportId);

    /**
     * 설명 : 신고 취소
     * @since : 2024.10.14
     * @author : T.S YUN
     */
    void revokeReport(int reportId);

}
