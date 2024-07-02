package com.gapco.backend.repository;

import com.gapco.backend.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    Optional<Company> findByInstitutionId(Integer institutionId);
    Optional<Company> findByName(String name);

    @Query(value = "SELECT max(institutionId) FROM Institution")
    int getMaxInstitutionId();

    @Query("SELECT c FROM Company c WHERE c.language = :language")
    Page<Company> getAll(String language, Pageable pageable);
}
