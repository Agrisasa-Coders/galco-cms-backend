package com.gapco.backend.repository;

import com.gapco.backend.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServiceRepository extends JpaRepository<Service,Long> {

    @Query("SELECT s FROM Service s WHERE s.language = :language")
    Page<Service> getAll(String language, Pageable pageable);
}
