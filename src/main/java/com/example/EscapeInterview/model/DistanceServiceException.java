package com.example.EscapeInterview.model;

public class DistanceServiceException {

    private int errorCode;
    private String message;

    public DistanceServiceException() {
    }

    public DistanceServiceException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}