package com.example.adminreportservice.service;

import com.example.adminreportservice.dto.ReportRequestDto;
import com.example.adminreportservice.dto.ReportResponseDto;

import java.util.List;

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

    /**
     * 설명 : 특정 사람의 총 신고 갯수 가져오기
     * @since : 2024.10.17
     * @author : T.S YUN
     */
    int getTotalCountReport(int memberId);

    /**
     * 설명 : 특정 사용자의 신고한 정보 가져오기
     * @since : 2024.10.17
     * @author : T.S YUN
     */
    List<ReportResponseDto> getReportsByMemberId(int memberId);
}
