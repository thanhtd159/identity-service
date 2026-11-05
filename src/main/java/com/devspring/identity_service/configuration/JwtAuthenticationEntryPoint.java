package com.devspring.identity_service.configuration;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.devspring.identity_service.dto.request.ApiResponse;
import com.devspring.identity_service.exception.ErrorCode;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        // TODO Auto-generated method stub

        ErrorCode errorCode = ErrorCode.USER_UNAUTHETICATION;
        response.setStatus(errorCode.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));

        response.flushBuffer();
        // throw new UnsupportedOperationException("Unimplemented method 'commence'");
    }

}
