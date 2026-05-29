package com.insureflow.user_service.repository;

import com.insureflow.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);
    boolean existsByMobileNumber(String mobileNumber);
}
