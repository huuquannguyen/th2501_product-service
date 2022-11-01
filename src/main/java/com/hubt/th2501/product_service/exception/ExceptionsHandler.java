package com.hubt.th2501.product_service.exception;

import com.hubt.th2501.product_service.constant.ErrorCode;
import com.hubt.th2501.product_service.model.ApiResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionsHandler {

    private final Log logger = LogFactory.getLog(ExceptionsHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResponse<String> handleBindExceptions(BindException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ApiResponse.failureWithCode("BAD_REQUEST", errors.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ApiResponse.failureWithCode("BAD_REQUEST", errors.toString());
    }

    @ExceptionHandler(value = ApiException.class)
    public ApiResponse<String> handleError(HttpServletRequest req, ApiException e) {
        logger.error("Request " + req.getRequestURL() + " raised " + e);
        return ApiResponse.failureWithCode(e.getErrorCode(), e.getErrorMsg());
    }

    @ExceptionHandler(value = Exception.class)
    public ApiResponse<String> handleError(HttpServletRequest req, Exception e) {
        logger.error("Request " + req.getRequestURL() + " raised " + e);
        return ApiResponse.failureWithCode(ErrorCode.UNKNOWN_ERROR.toString(), e.getMessage());
    }

}
