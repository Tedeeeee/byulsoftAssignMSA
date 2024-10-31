package com.example.usercommentservice.exception;

import com.example.usercommentservice.commonApi.BodyResponse;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity<BodyResponse<String>> callNotPermittedException(CallNotPermittedException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(BodyResponse.fail("서킷이 열렸습니다"));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BodyResponse<String>> handleCustomException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BodyResponse.fail(ex.getMessage()));
    }
}
