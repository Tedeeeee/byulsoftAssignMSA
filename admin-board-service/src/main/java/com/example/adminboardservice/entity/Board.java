package com.example.adminboardservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    private Integer boardId;
    private int memberId;
    private String boardTitle;
    private String boardContent;
    private String boardRegion;
    private int boardView;
    private LocalDateTime boardCreatedAt;
    private LocalDateTime boardUpdatedAt;
    private boolean boardIsDelete;
    private List<BoardStar> stars;
}
