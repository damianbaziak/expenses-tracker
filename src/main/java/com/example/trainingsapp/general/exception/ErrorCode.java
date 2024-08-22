package com.example.trainingsapp.general.exception;

public enum ErrorCode {
    U001("U001", "USER_ALREADY_EXISTS", 400),
    U002("U002", "INVALID CREDENTIALS", 401),
    U003("U003", "USER NOT FOUND", 404),

    FT001("FT001", "FINANCIAL_TRANSACTION_NOT_FOUND", 404),
    FT002("FT002", "FINANCIAL_TRANSACTION_TYPE_DOES_NOT_MATCH_WITH_CATEGORY_TYPE", 400),

    FTC001("FTC001", "FINANCIAL_TRANSACTION_CATEGORY_NOT_FOUND", 404),

    W001("W001", "WALLET NOT_FOUND", 404),
    W002("W002", "USER IS NOT WALLET OWNER", 403);

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
