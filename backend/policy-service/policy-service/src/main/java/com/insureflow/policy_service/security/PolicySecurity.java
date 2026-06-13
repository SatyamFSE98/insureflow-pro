package com.insureflow.policy_service.security;

import com.insureflow.policy_service.repository.PolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("policySecurity")
@RequiredArgsConstructor
public class PolicySecurity {

    private final PolicyRepository policyRepository;

    public boolean isPolicyOwner(Long policyId, Authentication authentication) {

        Long loggedInUserId = getLoggedInUserId(authentication);

        if (loggedInUserId == null) {
            return false;
        }

        return policyRepository.findById(policyId)
                .map(policy -> policy.getUserId().equals(loggedInUserId))
                .orElse(false);
    }

    public boolean isSameUser(Long userId, Authentication authentication) {

        Long loggedInUserId = getLoggedInUserId(authentication);

        if (loggedInUserId == null) {
            return false;
        }

        return loggedInUserId.equals(userId);
    }

    private Long getLoggedInUserId(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof JwtAuthenticationFilter.LoggedInUser loggedInUser) {
            return loggedInUser.getUserId();
        }

        return null;
    }
}