package com.insureflow.user_service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;


@Getter
@Setter
@Builder
public class ErrorResponse {

    private boolean success;
    private String message;
    private int statusCode;
    private String path;
    private Map<String, String> errors;
    private LocalDateTime timestamp;
}
