package com.insureflow.user_service.exception;


import com.insureflow.user_service.dto.response.ApiResponse;
import com.insureflow.user_service.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(
         ResourceAlreadyExistsException ex,
                 HttpServletRequest request){

            ErrorResponse response = ErrorResponse.builder()
                    .success(false)
                    .message(ex.getMessage())
                    .statusCode(HttpStatus.CONFLICT.value())
                    .path(request.getRequestURI())
                    .errors(null)
                    .timestamp(LocalDateTime.now())
                    .build();

            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
                    HttpServletRequest request){

        Map<String,String>  validationErrors = new HashMap<>();

        for(FieldError fieldError: ex.getBindingResult().getFieldErrors()){
            validationErrors.put(fieldError.getField(),fieldError.getDefaultMessage() );
        }

        ErrorResponse response = ErrorResponse.builder()
                .success(false)
                .message("validation failed")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .errors(validationErrors)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex,
            HttpServletRequest request
    ) {
        ErrorResponse response = ErrorResponse.builder()
                .success(false)
                .message("Duplicate or invalid database value")
                .statusCode(HttpStatus.CONFLICT.value())
                .path(request.getRequestURI())
                .errors(null)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        ErrorResponse response = ErrorResponse.builder()
                .success(false)
                .message("Something went wrong. Please try again later.")
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(request.getRequestURI())
                .errors(null)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new HashMap<>();

        errors.put("role", "Invalid role. Allowed values are ADMIN, AGENT, CUSTOMER");

        ErrorResponse response = ErrorResponse.builder()
                .success(false)
                .message("Invalid request body")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>>  handleInvalidCredentialsException(InvalidCredentialsException ex){
        ApiResponse<Object> response = ApiResponse.builder()
                .data(null)
                .message(ex.getMessage())
                .success(false)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }



}
