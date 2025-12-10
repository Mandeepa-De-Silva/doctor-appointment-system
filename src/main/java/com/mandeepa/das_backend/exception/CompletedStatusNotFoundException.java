package com.mandeepa.das_backend.exception;

public class CompletedStatusNotFoundException extends RuntimeException {
    public CompletedStatusNotFoundException(String message) {
        super(message);
    }
}
