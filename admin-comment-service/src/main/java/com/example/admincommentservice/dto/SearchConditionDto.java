package com.example.admincommentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchConditionDto {
    private String startDate;
    private String endDate;
    private int memberId;
    private int pageNumber;
    private int pageSize;
    private int offset;

    public int pageOffset() {
        return (pageNumber - 1) * pageSize;
    }

    public SearchConditionDto(String startDate, String endDate, int memberId, int pageNumber) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.memberId = memberId;
        this.pageNumber = pageNumber;
        this.pageSize = 5;
        this.offset = pageOffset();
    }

    public void checkValidationPageNumber(final int totalPage) {
        if (pageNumber < 1 || pageNumber > totalPage) {
            throw new RuntimeException("Invalid page number");
        }
    }
}
