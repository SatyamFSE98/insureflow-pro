package com.insureflow.policy_service.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class UserServiceErrorResponse {
    private Map<String, String> errors;
    private String message;
    private String path;
    private int statusCode;
    private boolean success;
    private LocalDateTime timestamp;
}
