package com.gapco.backend.service;

import com.gapco.backend.entity.User;

import java.time.LocalDateTime;

public interface LoginAuthorizationService {
    boolean isNoOfPasswordAttemptsAllowed(Long institutionId, int noOfPasswordAttempts);
    boolean isPasswordNotExpired(Long institutionId, LocalDateTime time);
    void incrementPasswordAttempt(User user);
    void decrementPasswordAttempt(User user);
    void resetPasswordAttempts(User user);
}
