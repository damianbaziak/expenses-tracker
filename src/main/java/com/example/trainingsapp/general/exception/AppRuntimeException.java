package com.example.trainingsapp.general.exception;

public class AppRuntimeException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String description;
    private final String status;
    private final String message;
    private final Integer httpStatusCode;

    public AppRuntimeException(ErrorCode errorCode, String description) {
        this.description = description;
        this.status = errorCode.getBusinessCode();
        this.message = errorCode.getBusinessMessage();
        this.httpStatusCode = errorCode.getHttpStatus();
        this.errorCode = errorCode;

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

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
