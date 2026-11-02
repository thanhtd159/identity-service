package com.devspring.identity_service.exception;

public enum ErrorCode {
    USER_EXISTED(1002, "Username is already existed"),
    UNKNOWN_ERROR(9999, "Unknown error"),
    USER_NOT_FOUND(1006, "User not found"),

    INVALID_REQUEST(1003, "Invalid request"),
    INVALID_PASSWORD(1004, "Invalid password length. Password must be at least 8 characters."),
    INVALID_USERNAME(1005, "Invalid username length. Username must be at least 3 characters."),
    INVALID_CREDENTIALS(1007, "Invalid credentials");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
