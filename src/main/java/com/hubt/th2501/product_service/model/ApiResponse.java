package com.hubt.th2501.product_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse <T>{

    T result;
    String errorCode;
    Object message;
    int responseCode;

    public static <T> ApiResponse<T> successWithResult(T result){
        return new ApiResponse<>(result, null, HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value());
    }

    public static <T> ApiResponse<T> failureWithCode(String errorCode, String message){
        return new ApiResponse<>(null, errorCode, message, HttpStatus.BAD_REQUEST.value());
    }

    public static <T> ApiResponse<T> failureWithCode(String errorCode, String message, T result){
        return new ApiResponse<>(result, errorCode, message, HttpStatus.BAD_REQUEST.value());
    }
}
