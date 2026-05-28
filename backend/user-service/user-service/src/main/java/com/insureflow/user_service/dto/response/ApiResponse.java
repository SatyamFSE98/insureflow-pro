package com.insureflow.user_service.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ApiResponse<T>{

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
}
