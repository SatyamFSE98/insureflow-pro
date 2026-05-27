package com.insureflow.user_service.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthResponse {

    private String service;
    private String status;
    private String message;
}
