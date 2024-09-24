package com.gapco.backend.repository;


import com.gapco.backend.entity.TermsAndCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TermsAndConditionRepository extends JpaRepository<TermsAndCondition, Long> {
    @Query("SELECT terms FROM TermsAndCondition terms WHERE terms.language = :language")
    Page<TermsAndCondition> getAll(String language, Pageable pageable);
}
