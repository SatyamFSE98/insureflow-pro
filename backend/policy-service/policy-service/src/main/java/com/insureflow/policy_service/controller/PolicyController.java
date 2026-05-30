package com.insureflow.policy_service.controller;


import com.insureflow.policy_service.constant.AppConstants;
import com.insureflow.policy_service.dto.request.CreatePolicyRequest;
import com.insureflow.policy_service.dto.response.ApiResponse;
import com.insureflow.policy_service.dto.response.PolicyResponse;
import com.insureflow.policy_service.service.PolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;


    @PostMapping
    public ResponseEntity<ApiResponse<PolicyResponse>> createPolicy(@Valid @RequestBody CreatePolicyRequest request){
        PolicyResponse policyResponse = policyService.createPolicy(request);

        ApiResponse<PolicyResponse> response = ApiResponse.<PolicyResponse>builder()
                .data(policyResponse)
                .message(AppConstants.POLICY_CREATED_SUCCESSFULLY)
                .success(true)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
