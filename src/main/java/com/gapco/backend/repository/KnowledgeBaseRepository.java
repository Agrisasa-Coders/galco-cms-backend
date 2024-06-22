package com.gapco.backend.repository;

import com.gapco.backend.entity.KnowledgeBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeBaseRepository extends JpaRepository<KnowledgeBase,Long> {
}

