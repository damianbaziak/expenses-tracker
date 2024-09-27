package com.example.trainingsapp.general.exception.error;

public class ErrorResponseDTO {
    private String status; // "W001",
    private String message; //  "WALLETS_RETRIEVING_ERROR",
    private String description; // "Wallet with id: is not found in the database",
    private Integer statusCode; // 404,

    public ErrorResponseDTO(String status, String message,
                            String description,
                            Integer statusCode) {
        this.status = status;
        this.message = message;
        this.description = description;
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
