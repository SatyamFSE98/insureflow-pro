package com.insureflow.policy_service.dto.response;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long userId;
    private String fullName;
    private String email;
    private String mobileNumber;
    private String role;
    private boolean active;
    private LocalDateTime createdAt;
}
