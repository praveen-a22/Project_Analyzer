package com.projectanalyzer.dto.common;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {

    private boolean success;
    private int status;
    private String message;
    private Map<String, String> errors;
    private LocalDateTime timestamp;

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(boolean success, int status, String message, Map<String, String> errors, LocalDateTime timestamp) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }

    public static ErrorResponse of(int status, String message, Map<String, String> errors) {
        return new ErrorResponse(false, status, message, errors, LocalDateTime.now());
    }

    public static ErrorResponse of(int status, String message) {
        return of(status, message, null);
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Map<String, String> getErrors() { return errors; }
    public void setErrors(Map<String, String> errors) { this.errors = errors; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
