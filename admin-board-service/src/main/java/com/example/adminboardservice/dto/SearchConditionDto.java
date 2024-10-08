package com.example.adminboardservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchConditionDto {
    private String searchType;
    private String searchText;
    private String startDate;
    private String endDate;
    private int memberId;
    private int pageNumber;
    private int pageSize;
    private int offset;

    public int pageOffset() {
        return (pageNumber - 1) * pageSize;
    }

    public SearchConditionDto(String searchType, String searchText, String startDate, String endDate, int memberId, int pageNumber) {
        this.searchType = searchType;
        this.searchText = searchText;
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