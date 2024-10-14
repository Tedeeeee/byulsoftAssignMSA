package com.example.adminboardservice.exception;

import com.example.adminboardservice.commonApi.BodyResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BodyResponse<String>> handleCustomException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BodyResponse.fail(ex.getMessage()));
    }
}
