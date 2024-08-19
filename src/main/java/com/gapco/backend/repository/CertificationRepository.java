package com.gapco.backend.repository;

import com.gapco.backend.entity.Certification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CertificationRepository extends JpaRepository<Certification,Long> {

    @Query("SELECT cert FROM Certification cert WHERE cert.language = :language")
    Page<Certification> getAll(String language, Pageable pageable);
}
