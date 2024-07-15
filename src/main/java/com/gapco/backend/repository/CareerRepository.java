package com.gapco.backend.repository;

import com.gapco.backend.entity.Career;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CareerRepository extends JpaRepository<Career,Long> {

    @Query("SELECT c FROM Career c WHERE c.language = :language")
    Page<Career> getAll(String language, Pageable pageable);
}
