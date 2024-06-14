package com.example.trainingsapp.general.exception;

public enum ErrorCode {
    U001("U001", "USER_ALREADY_EXISTS", 400);

    private final String status;
    private final String message;
    private final Integer statusCode;

    ErrorCode(String status, String message, Integer statusCode) {
        this.status = status;
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
