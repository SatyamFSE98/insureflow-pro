package com.insureflow.policy_service.repository;

import com.insureflow.policy_service.entity.Policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy,Long> {

    boolean existsByPolicyNumber(String policyNumber);

    Page<Policy> findByUserId(Long userId, Pageable pageable);
}
