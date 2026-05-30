package com.insureflow.policy_service.dto.request;

import com.insureflow.policy_service.entity.enums.PolicyType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePolicyRequest {
    @NotNull(message = "User id is required")
    private Long userId;

    @NotBlank(message = "Policy name is required")
    private String policyName;

    @NotNull(message = "Policy type is required")
    private PolicyType policyType;

    @NotNull(message = "Sum insured is required")
    @DecimalMin(value = "10000.0", message = "Sum insured must be at least 10000")
    private BigDecimal sumInsured;

    @NotNull(message = "Premium amount is required")
    @DecimalMin(value = "100.0", message = "Premium amount must be at least 100")
    private BigDecimal premiumAmount;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 year")
    private Integer durationInYears;
}
