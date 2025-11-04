package com.mandeepa.das_backend.exception;

public class DuplicateFoundException extends RuntimeException {
    public DuplicateFoundException(String message) {
        super(message);
    }
}
