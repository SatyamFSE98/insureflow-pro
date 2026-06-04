package com.insureflow.user_service.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class InstanceController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/api/v1/users/instance")
    public Map<String, String> getInstanceInfo() {
        return Map.of(
                "service", "user-service",
                "port", serverPort
        );
    }
}


//mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=8083"