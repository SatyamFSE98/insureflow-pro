package com.insureflow.policy_service.entity;


import com.insureflow.policy_service.entity.enums.PolicyStatus;
import com.insureflow.policy_service.entity.enums.PolicyType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "policies",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_policy_number", columnNames = "policy_number")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id")
    private Long policyId;

    @Column(name = "policy_number", nullable = false, unique = true)
    private String policyNumber;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "policy_name", nullable = false, length = 150)
    private String policyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "policy_type", nullable = false, length = 30)
    private PolicyType policyType;

    @Column(name = "sum_insured", nullable = false)
    private BigDecimal sumInsured;

    @Column(name = "premium_amount", nullable = false)
    private BigDecimal premiumAmount;

    @Column(name = "duration_in_years", nullable = false)
    private Integer durationInYears;

    @Enumerated(EnumType.STRING)
    @Column(name = "policy_status", nullable = false, length = 30)
    private PolicyStatus policyStatus;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
