package com.insureflow.policy_service.client;

import com.insureflow.policy_service.config.FeignClientConfig;
import com.insureflow.policy_service.dto.response.ApiResponse;
import com.insureflow.policy_service.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service",
        configuration = FeignClientConfig.class
)
public interface UserServiceClient {

    @GetMapping("/api/v1/users/{userId}")
    ApiResponse<UserResponse> getUserBYId(@PathVariable("userId") Long userId);
}
