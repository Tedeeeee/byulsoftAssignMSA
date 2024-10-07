package com.example.userboardservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchConditionDto {
    private String sortOrder;
    private String sortType;
    private String searchType;
    private String searchText;
    private int pageNumber;
    public int offset;

    public int pageOffset() {
        int pageSize = 5;
        return (pageNumber - 1) * pageSize;
    }

    public SearchConditionDto(String sortOrder, String sortType, String searchType, String searchText, int pageNumber) {
        this.sortOrder = sortOrder;
        this.sortType = sortType;
        this.searchType = searchType;
        this.searchText = searchText;
        this.pageNumber = pageNumber;
        this.offset = pageOffset();
    }

    public boolean isSortOrderEmpty() {
        return sortOrder.isEmpty();
    }
}
