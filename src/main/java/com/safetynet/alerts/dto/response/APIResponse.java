package com.safetynet.alerts.dto.response;

public class APIResponse<T> {

    private int statusCode;

    private String message;

    private T payload;

    public APIResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public APIResponse(int statusCode, String message, T payload) {
        this(statusCode, message);
        this.payload = payload;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
