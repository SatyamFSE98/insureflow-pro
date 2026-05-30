package com.insureflow.policy_service.dto.response;

import com.insureflow.policy_service.entity.enums.PolicyStatus;
import com.insureflow.policy_service.entity.enums.PolicyType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
public class PolicyResponse {
    private Long policyId;
    private String policyNumber;
    private Long userId;
    private String policyName;
    private PolicyType policyType;
    private BigDecimal sumInsured;
    private BigDecimal premiumAmount;
    private Integer durationInYears;
    private PolicyStatus policyStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
}
