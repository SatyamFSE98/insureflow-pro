package com.insureflow.user_service.dto.response;


import com.insureflow.user_service.entity.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UserResponse {

    private Long userId;
    private String fullName;
    private String email;
    private String mobileNumber;
    private UserRole role;
    private Boolean active;
    private LocalDateTime createdAt;
}
