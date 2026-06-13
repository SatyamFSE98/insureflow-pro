package com.insureflow.policy_service.controller;


import com.insureflow.policy_service.constant.AppConstants;
import com.insureflow.policy_service.dto.request.CreatePolicyRequest;
import com.insureflow.policy_service.dto.response.ApiResponse;
import com.insureflow.policy_service.dto.response.PageResponse;
import com.insureflow.policy_service.dto.response.PolicyResponse;
import com.insureflow.policy_service.service.PolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
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

    @PreAuthorize("hasRole('ADMIN') or @policySecurity.isPolicyOwner(#policyId, authentication)")
    @GetMapping("/{policyId}")
    public ResponseEntity<ApiResponse<PolicyResponse>> getPolicyById(@PathVariable Long policyId){
        PolicyResponse policyResponse = policyService.getPolicyById(policyId);

        ApiResponse<PolicyResponse> response = ApiResponse.<PolicyResponse>builder()
                .data(policyResponse)
                .message("policy fetched successfully")
                .success(true)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PolicyResponse>>> getAllPolicies(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "policyId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        PageResponse<PolicyResponse> policies = policyService.getAllPolicies(
                pageNo,
                pageSize,
                sortBy,
                sortDir
        );

        ApiResponse<PageResponse<PolicyResponse>> response =
                ApiResponse.<PageResponse<PolicyResponse>>builder()
                        .data(policies)
                        .message("Policies fetched successfully")
                        .success(true)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN') or @policySecurity.isSameUser(#userId, authentication)")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<PageResponse<PolicyResponse>>> getPoliciesByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "policyId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        PageResponse<PolicyResponse> policies = policyService.getPoliciesByUserId(
                userId,
                pageNo,
                pageSize,
                sortBy,
                sortDir
        );

        ApiResponse<PageResponse<PolicyResponse>> response =
                ApiResponse.<PageResponse<PolicyResponse>>builder()
                        .data(policies)
                        .message("User policies fetched successfully")
                        .success(true)
                        .timestamp(LocalDateTime.now())
                        .build();

        return ResponseEntity.ok(response);
    }

}
