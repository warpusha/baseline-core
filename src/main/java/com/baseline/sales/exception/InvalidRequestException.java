package com.baseline.sales.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message, Object request) {
        super(String.format("Invalid request! %s: %s", message, request.toString()));
    }
}
