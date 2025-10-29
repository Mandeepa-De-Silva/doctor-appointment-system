package com.mandeepa.das_backend.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
public class ErrorResponse{
    private final Instant timestamp;
    private final int status;
    private final String errorCode;
    private final String message;
    private final String path;

    public ErrorResponse(HttpStatus status, String message, String path) {
        this.timestamp = Instant.now();
        this.status = status.value();
        this.errorCode = status.getReasonPhrase();
        this.message = message;
        this.path = path;
    }
}
