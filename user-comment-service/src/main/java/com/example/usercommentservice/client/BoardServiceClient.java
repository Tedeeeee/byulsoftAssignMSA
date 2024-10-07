package com.example.usercommentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userBoardServiceClient", url = "http://localhost:8888")
public interface BoardServiceClient {

    @GetMapping("/api/userBoardService/feign/boards/{boardId}")
    boolean getBoardByBoardId(@PathVariable int boardId);
}
