package com.gapco.backend.repository;

import com.gapco.backend.entity.UserRequestOTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserOTPRepository extends JpaRepository<UserRequestOTP,Long> {
    Optional<UserRequestOTP> findByUserId(String userId);
}
