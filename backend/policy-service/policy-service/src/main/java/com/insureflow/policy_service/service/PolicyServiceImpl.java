package com.insureflow.policy_service.service;

import com.insureflow.policy_service.dto.request.CreatePolicyRequest;
import com.insureflow.policy_service.dto.response.PageResponse;
import com.insureflow.policy_service.dto.response.PolicyResponse;
import com.insureflow.policy_service.entity.Policy;
import com.insureflow.policy_service.entity.enums.PolicyStatus;
import com.insureflow.policy_service.exception.PolicyNotFoundException;
import com.insureflow.policy_service.repository.PolicyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public PolicyResponse getPolicyById(Long policyId) {
       Policy policy = policyRepository.findById(policyId)
               .orElseThrow(() -> new PolicyNotFoundException(
                       "Policy not found with id: " + policyId
               ));
       return mapToPolicyResponse(policy);
    }

    @Override
    public PageResponse<PolicyResponse> getAllPolicies(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        Page<Policy> policyPage = policyRepository.findAll(pageable);

        List<PolicyResponse> policies = policyPage.getContent()
                .stream().map(this::mapToPolicyResponse).collect(Collectors.toList());

        return buildPageResponse(policyPage,policies);

        /*
        List<PolicyResponse> policies = new ArrayList<>();

         for (Policy policy : policyPage.getContent()) {
           PolicyResponse response = mapToPolicyResponse(policy);
           policies.add(response);
         }
         */
    }

    @Override
    public PageResponse<PolicyResponse> getPoliciesByUserId(Long userId, int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ?Sort.by(sortBy).descending()
                :Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        Page<Policy> policyPage = policyRepository.findByUserId(userId,pageable);

        List<PolicyResponse> policies = policyPage.getContent().stream()
                .map(this::mapToPolicyResponse).collect(Collectors.toList());

        return buildPageResponse(policyPage,policies);
    }
    private PageResponse<PolicyResponse> buildPageResponse(
            Page<Policy> policyPage,
            List<PolicyResponse> policies) {

        return PageResponse.<PolicyResponse>builder()
                .content(policies)
                .pageNo(policyPage.getNumber())
                .pageSize(policyPage.getSize())
                .totalElements(policyPage.getTotalElements())
                .totalPages(policyPage.getTotalPages())
                .last(policyPage.isLast())
                .build();
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
