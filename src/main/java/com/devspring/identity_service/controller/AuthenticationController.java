package com.devspring.identity_service.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.*;

import com.devspring.identity_service.dto.request.ApiResponse;
import com.devspring.identity_service.dto.request.AuthenticationRequest;
import com.devspring.identity_service.dto.request.IntrospectRequest;
import com.devspring.identity_service.dto.response.AuthenticationResponse;
import com.devspring.identity_service.dto.response.IntrospectResponse;
import com.devspring.identity_service.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        var response = authenticationService.isAuthenticated(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(1000)
                .message("User authenticated successfully")
                .result(response)
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws JOSEException, ParseException {
        var response = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .code(1000)
                .message("Token introspected successfully")
                .result(response)
                .build();
    }
}
