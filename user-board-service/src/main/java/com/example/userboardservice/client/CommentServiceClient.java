package com.example.userboardservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "commentServiceClient", url = "http://localhost:8888")
public interface CommentServiceClient {
    @PostMapping("/api/userCommentService/noAuth/boards/feign/{boardId}")
    void deleteCommentByBoardId(@PathVariable("boardId") int boardId);
}
