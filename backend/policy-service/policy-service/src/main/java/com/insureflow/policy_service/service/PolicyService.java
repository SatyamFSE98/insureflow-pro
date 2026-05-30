package com.insureflow.policy_service.service;

import com.insureflow.policy_service.dto.request.CreatePolicyRequest;
import com.insureflow.policy_service.dto.response.PolicyResponse;

public interface PolicyService {
    PolicyResponse createPolicy(CreatePolicyRequest request);
}
