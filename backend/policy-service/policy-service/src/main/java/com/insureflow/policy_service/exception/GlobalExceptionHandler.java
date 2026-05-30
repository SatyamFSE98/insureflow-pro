package com.insureflow.policy_service.exception;

import com.insureflow.policy_service.constant.AppConstants;
import com.insureflow.policy_service.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(
                        error.getField(),
                        error.getDefaultMessage()
                ));

        ApiResponse<Map<String, String>> response =
                ApiResponse.<Map<String, String>>builder()
                        .data(errors)
                        .message(AppConstants.VALIDATION_FAILED)
                        .success(false)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGlobalException(Exception ex) {

        ex.printStackTrace();

        ApiResponse<Object> response = ApiResponse.builder()
                .data(null)
                .message(AppConstants.SOMETHING_WENT_WRONG)
                .success(false)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
