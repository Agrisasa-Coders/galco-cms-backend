package com.gapco.backend.repository;

import com.gapco.backend.entity.Blog;
import com.gapco.backend.entity.KnowledgeBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KnowledgeBaseRepository extends JpaRepository<KnowledgeBase,Long> {

    @Query("SELECT k FROM KnowledgeBase k WHERE k.language = :language")
    Page<KnowledgeBase> getAll(String language, Pageable pageable);

    @Query("SELECT k FROM KnowledgeBase k WHERE k.service.id = :serviceId")
    List<KnowledgeBase> getKnowledgeBases(Long serviceId);
}

