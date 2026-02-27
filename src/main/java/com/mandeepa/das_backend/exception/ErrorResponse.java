package com.mandeepa.das_backend.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final Instant timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final Map<String, String> validationErrors;

    public ErrorResponse(HttpStatus status, String message, String path) {
        this(status, message, path, null);
    }

    public ErrorResponse(HttpStatus status, String message, String path,
                         Map<String, String> validationErrors) {
        this.timestamp = Instant.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
        this.validationErrors = validationErrors;
    }
}
