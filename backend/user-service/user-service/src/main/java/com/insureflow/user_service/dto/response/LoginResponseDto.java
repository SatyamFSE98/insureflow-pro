package com.insureflow.user_service.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private Long userId;
    private String fullName;
    private String email;
    private String role;
    private boolean active;
    private String token;
    private String tokenType;

}

