package com.github.polydome.remedy.api.model;

import org.springframework.http.HttpStatus;

public class ApiError {
    private final int code;
    private final String status;
    private final String message;

    public ApiError(HttpStatus status, String message) {
        this.code = status.value();
        this.status = status.name();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
