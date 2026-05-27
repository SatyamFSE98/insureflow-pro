package com.insureflow.user_service.controller;


import com.insureflow.user_service.dto.response.HealthResponse;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserHealthController {


    @GetMapping("/health")
    public ResponseEntity<HealthResponse>  healthCheck(){

        HealthResponse healthResponse = HealthResponse.builder()
                        .service("user-service")
                        .status("UP")
                        .message("User Service is running").
                         build();

        return ResponseEntity.ok(healthResponse);
    }
}
