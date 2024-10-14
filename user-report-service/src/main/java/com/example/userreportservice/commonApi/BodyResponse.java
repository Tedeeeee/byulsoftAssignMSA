package com.example.userreportservice.commonApi;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BodyResponse<T> {
    private T body;
    private String message;

    public BodyResponse(T body, String message) {
        this.body = body;
        this.message = message;
    }

    public static <T> BodyResponse<T> success(final T body, final String message) {
        return new BodyResponse<>(body, message);
    }

    public static <T> BodyResponse<T> success(final String message) {
        return new BodyResponse<>(null, message);
    }

    public static <T> BodyResponse<T> success(final T body) {
        return new BodyResponse<>(body, null);
    }

    public static <T> BodyResponse<T> createSuccess(final String message) {
        return new BodyResponse<>(null, message);
    }

    public static <T> BodyResponse<T> fail(final String errorMessage) {
        return new BodyResponse<>(null, errorMessage);
    }
}
