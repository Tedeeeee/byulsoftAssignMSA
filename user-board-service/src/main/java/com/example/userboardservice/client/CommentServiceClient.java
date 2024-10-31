package com.example.userboardservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "commentServiceClient", url = "http://user-comment-service:8086")
public interface CommentServiceClient {

    @PostMapping("/noAuth/comments/feign/{boardId}")
    void deleteCommentByBoardId(@PathVariable("boardId") int boardId);
}
