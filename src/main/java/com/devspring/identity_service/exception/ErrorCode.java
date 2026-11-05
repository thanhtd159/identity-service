package com.devspring.identity_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXISTED(1002, "Username is already existed", HttpStatus.BAD_REQUEST),
    UNKNOWN_ERROR(9999, "Unknown error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_FOUND(1006, "User not found", HttpStatus.NOT_FOUND),

    INVALID_REQUEST(1003, "Invalid request", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Invalid password length. Password must be at least 8 characters.", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(1005, "Invalid username length. Username must be at least 3 characters.", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS(1007, "Invalid credentials", HttpStatus.BAD_REQUEST),
    USER_UNAUTHETICATION(1008, "Un-authentication", HttpStatus.UNAUTHORIZED),
    USER_UNAUTHORIZED(1009, "Un-authorized", HttpStatus.FORBIDDEN);

    int code;
    String message;
    HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
