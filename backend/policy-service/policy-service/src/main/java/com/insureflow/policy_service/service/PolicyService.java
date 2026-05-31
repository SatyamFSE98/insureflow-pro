package com.insureflow.policy_service.service;

import com.insureflow.policy_service.dto.request.CreatePolicyRequest;
import com.insureflow.policy_service.dto.response.PageResponse;
import com.insureflow.policy_service.dto.response.PolicyResponse;

public interface PolicyService {
    PolicyResponse createPolicy(CreatePolicyRequest request);

    PolicyResponse getPolicyById(Long policyId);

    PageResponse<PolicyResponse> getAllPolicies(
            int pageNo,
            int pageSize,
            String sortBy,
            String sortDir
    );

    PageResponse<PolicyResponse> getPoliciesByUserId(
            Long userId,
            int pageNo,
            int pageSize,
            String sortBy,
            String sortDir
    );
}
