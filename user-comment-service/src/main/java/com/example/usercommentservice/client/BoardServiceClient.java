package com.example.usercommentservice.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userBoardServiceClient", url = "http://user-board-service:8084")
public interface BoardServiceClient {

    @GetMapping("/noAuth/boards/feign/{boardId}")
    boolean getBoardByBoardId(@PathVariable int boardId);
}
