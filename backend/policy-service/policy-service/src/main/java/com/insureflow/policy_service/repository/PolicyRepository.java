package com.insureflow.policy_service.repository;

import com.insureflow.policy_service.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy,Long> {

    boolean existsByPolicyNumber(String policyNumber);
}
