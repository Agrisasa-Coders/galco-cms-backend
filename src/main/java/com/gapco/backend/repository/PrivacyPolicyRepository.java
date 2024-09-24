package com.gapco.backend.repository;

import com.gapco.backend.entity.PrivacyPolicy;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PrivacyPolicyRepository  extends JpaRepository<PrivacyPolicy,Long> {
    @Query("SELECT policy FROM PrivacyPolicy policy WHERE policy.language = :language")
    Page<PrivacyPolicy> getAll(String language, Pageable pageable);
}
