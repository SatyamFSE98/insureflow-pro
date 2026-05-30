package com.insureflow.policy_service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponse<T> {
    private T data;
    private String message;
    private boolean success;
    private LocalDateTime timestamp;
}
