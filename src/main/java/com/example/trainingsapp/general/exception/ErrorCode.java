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

    private final String businessStatus;
    private final String businessMessage;
    private final Integer businessStatusCode;

    ErrorCode(String status, String message, Integer statusCode) {
        this.businessStatus = status;
        this.businessMessage = message;
        this.businessStatusCode = statusCode;
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public String getBusinessMessage() {
        return businessMessage;
    }

    public Integer getBusinessStatusCode() {
        return businessStatusCode;
    }
}
