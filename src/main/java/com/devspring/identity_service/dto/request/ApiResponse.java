package com.devspring.identity_service.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(Include.NON_NULL)
public class ApiResponse<T> {
    int code;
    String message;
    T result;

    // public int getCode() {
    // return code;
    // }

    // public void setCode(int code) {
    // this.code = code;
    // }

    // public String getMessage() {
    // return message;
    // }

    // public void setMessage(String message) {
    // this.message = message;
    // }

    // public T getResult() {
    // return result;
    // }

    // public void setResult(T result) {
    // this.result = result;
    // }

    // public ApiResponse(int code, String message, T result) {
    // this.code = code;
    // this.message = message;
    // this.result = result;
    // }
}
