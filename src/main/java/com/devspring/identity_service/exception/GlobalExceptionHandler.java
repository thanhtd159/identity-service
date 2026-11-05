package com.devspring.identity_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devspring.identity_service.dto.request.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<?>> handleAppException(AppException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ApiResponse<Object> response = new ApiResponse<>();

        response.setMessage(errorCode.getMessage());
        response.setCode(errorCode.getCode());
        response.setResult(null);

        return ResponseEntity.status(errorCode.getStatusCode()).body(response);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorCode errorCode = ErrorCode.USER_UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception ex) {
        ErrorCode errorCode = ErrorCode.UNKNOWN_ERROR;
        ApiResponse<Object> response = new ApiResponse<>();

        response.setMessage(errorCode.getMessage());
        response.setCode(errorCode.getCode());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleException(MethodArgumentNotValidException ex) {
        String enumKey = ex.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);

        ApiResponse<Object> response = new ApiResponse<>();
        response.setMessage(errorCode.getMessage());
        response.setCode(errorCode.getCode());

        return ResponseEntity.badRequest().body(response);
    }
}
