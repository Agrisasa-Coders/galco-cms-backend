package com.gapco.backend.repository;


import com.gapco.backend.entity.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface InstitutionRepository extends JpaRepository<Institution,Long> {
    Optional<Institution> findByInstitutionId(Integer institutionId);
    Optional<Institution> findByName(String name);

    @Query(value = "SELECT max(institutionId) FROM Institution")
    int getMaxInstitutionId();

    @Query("SELECT i FROM Institution i WHERE i.language = :language")
    Page<Institution> getAll(String language, Pageable pageable);
}
