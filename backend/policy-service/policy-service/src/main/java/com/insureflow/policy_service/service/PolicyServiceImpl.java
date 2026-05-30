package com.insureflow.policy_service.service;

import com.insureflow.policy_service.dto.request.CreatePolicyRequest;
import com.insureflow.policy_service.dto.response.PolicyResponse;
import com.insureflow.policy_service.entity.Policy;
import com.insureflow.policy_service.entity.enums.PolicyStatus;
import com.insureflow.policy_service.repository.PolicyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PolicyServiceImpl implements PolicyService{

    private final PolicyRepository policyRepository;

    public PolicyServiceImpl(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }


    @Override
    public PolicyResponse createPolicy(CreatePolicyRequest request) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(request.getDurationInYears());

        Policy policy = Policy.builder()
                .policyNumber(generatePolicyNumber())
                .userId(request.getUserId())
                .policyName(request.getPolicyName())
                .policyType(request.getPolicyType())
                .sumInsured(request.getSumInsured())
                .premiumAmount(request.getPremiumAmount())
                .durationInYears(request.getDurationInYears())
                .policyStatus(PolicyStatus.ACTIVE)
                .startDate(startDate)
                .endDate(endDate)
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .build();

        Policy savedPolicy = policyRepository.save(policy);
        return mapToPolicyResponse(savedPolicy);
    }

    private PolicyResponse mapToPolicyResponse(Policy policy) {
        return PolicyResponse.builder().
                policyId(policy.getPolicyId())
                .policyNumber(policy.getPolicyNumber())
                .userId(policy.getUserId())
                .policyName(policy.getPolicyName())
                .policyType(policy.getPolicyType())
                .sumInsured(policy.getSumInsured())
                .premiumAmount(policy.getSumInsured())
                .durationInYears(policy.getDurationInYears())
                .policyStatus(policy.getPolicyStatus())
                .startDate(policy.getStartDate())
                .endDate(policy.getEndDate())
                .createdAt(policy.getCreatedAt())
                .build();

    }
    private String generatePolicyNumber() {
        return "POL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
