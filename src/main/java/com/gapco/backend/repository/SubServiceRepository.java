package com.gapco.backend.repository;

import com.gapco.backend.entity.KnowledgeBase;
import com.gapco.backend.entity.SubService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubServiceRepository extends JpaRepository<SubService,Long> {

    @Query("SELECT sub FROM SubService sub WHERE sub.language = :language")
    Page<SubService> getAll(String language, Pageable pageable);

    @Query("SELECT sub FROM SubService sub WHERE sub.service.id = :serviceId")
    List<SubService> getSubServices(Long serviceId);

}
