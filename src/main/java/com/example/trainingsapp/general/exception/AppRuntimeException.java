package com.example.trainingsapp.general.exception;

public class AppRuntimeException extends RuntimeException {
    private final String description;
    private final String status;
    private final String message;
    private final Integer statusCode;

    public AppRuntimeException(ErrorCode errorCode, String description) {
        this.description = description;
        this.status = errorCode.getBusinessStatus();
        this.message = errorCode.getBusinessMessage();
        this.statusCode = errorCode.getBusinessStatusCode();

    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
